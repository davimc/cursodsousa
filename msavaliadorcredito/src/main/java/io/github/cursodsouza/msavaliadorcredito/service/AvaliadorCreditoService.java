package io.github.cursodsouza.msavaliadorcredito.service;

import feign.FeignException;
import io.github.cursodsouza.msavaliadorcredito.infra.client.CartoesResourceClient;
import io.github.cursodsouza.msavaliadorcredito.infra.client.ClienteResourceClient;
import io.github.cursodsouza.msavaliadorcredito.model.CartaoCliente;
import io.github.cursodsouza.msavaliadorcredito.model.DadosCliente;
import io.github.cursodsouza.msavaliadorcredito.model.SituacaoCliente;
import io.github.cursodsouza.msavaliadorcredito.service.ex.DadosClienteNotFoundException;
import io.github.cursodsouza.msavaliadorcredito.service.ex.ErroComunicacaoMicroServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;
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
}
