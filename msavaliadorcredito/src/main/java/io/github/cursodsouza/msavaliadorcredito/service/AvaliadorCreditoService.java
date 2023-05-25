package io.github.cursodsouza.msavaliadorcredito.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import io.github.cursodsouza.msavaliadorcredito.infra.client.CartoesResourceClient;
import io.github.cursodsouza.msavaliadorcredito.infra.client.ClienteResourceClient;
import io.github.cursodsouza.msavaliadorcredito.infra.mqueue.SolicitacaoEmissaoCartaoPublisher;
import io.github.cursodsouza.msavaliadorcredito.model.*;
import io.github.cursodsouza.msavaliadorcredito.service.ex.DadosClienteNotFoundException;
import io.github.cursodsouza.msavaliadorcredito.service.ex.ErroComunicacaoMicroServiceException;
import io.github.cursodsouza.msavaliadorcredito.service.ex.ErroSolicitacaoCartaoException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;
    private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;
    public SituacaoCliente obterSituacaoCliente(String cpf) throws DadosClienteNotFoundException, ErroComunicacaoMicroServiceException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponseEntity = clientesClient.findCpf(cpf);
            ResponseEntity<List<CartaoCliente>> cartoesResponseEntity = cartoesClient.getCartoesByCpf(cpf);
            return SituacaoCliente
                    .builder()
                    .cliente(dadosClienteResponseEntity.getBody())
                    .cartoesCliente(cartoesResponseEntity.getBody())
                    .build();
        }catch (FeignException.FeignClientException e) {
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroServiceException(e.getMessage(), status);
        }
    }

    public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda) throws DadosClienteNotFoundException, ErroComunicacaoMicroServiceException {
        try {
            ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.findCpf(cpf);
            ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.findByRendaMaiorOuIgual(BigDecimal.valueOf(renda));

            List<Cartao> cartoes = cartoesResponse.getBody();
            var listAprovados = cartoes.stream().map(c -> {
                CartaoAprovado obj = new CartaoAprovado();
                obj.setBandeira(c.getBandeira());
                obj.setCartao(c.getNome());

                BigDecimal limiteBasico = c.getLimiteBasico();
                BigDecimal idadeBD = BigDecimal.valueOf(dadosClienteResponse.getBody().getIdade());
                var fator = idadeBD.divide(BigDecimal.valueOf(10));
                BigDecimal limiteAprovado = fator.multiply(limiteBasico);
                obj.setLimiteAprovado(limiteAprovado);

                return obj;
            }).collect(Collectors.toList());

            return new RetornoAvaliacaoCliente(listAprovados);
        }catch (FeignException.FeignClientException e) {
            int status = e.status();
            if(HttpStatus.NOT_FOUND.value() == status) {
                throw new DadosClienteNotFoundException();
            }
            throw new ErroComunicacaoMicroServiceException(e.getMessage(), status);
        }
    }
    public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados) {
        try {
            emissaoCartaoPublisher.solicitarCartao(dados);
            var protocolo = UUID.randomUUID().toString();

            return new ProtocoloSolicitacaoCartao(protocolo);
        }catch (Exception e) {
            throw new ErroSolicitacaoCartaoException(e.getMessage());
        }
    }
}
