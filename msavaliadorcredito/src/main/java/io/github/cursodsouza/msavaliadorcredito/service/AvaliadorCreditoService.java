package io.github.cursodsouza.msavaliadorcredito.service;

import io.github.cursodsouza.msavaliadorcredito.infra.client.CartoesResourceClient;
import io.github.cursodsouza.msavaliadorcredito.infra.client.ClienteResourceClient;
import io.github.cursodsouza.msavaliadorcredito.model.CartaoCliente;
import io.github.cursodsouza.msavaliadorcredito.model.DadosCliente;
import io.github.cursodsouza.msavaliadorcredito.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    private final CartoesResourceClient cartoesClient;
    public SituacaoCliente obterSituacaoCliente(String cpf) {
        ResponseEntity<DadosCliente> dadosClienteResponseEntity = clientesClient.findCpf(cpf);
        ResponseEntity<List<CartaoCliente>> cartoesResponseEntity = cartoesClient.getCartoesByCpf(cpf);
        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponseEntity.getBody())
                .cartoesCliente(cartoesResponseEntity.getBody())
                .build();
    }
}
