package io.github.cursodsouza.msavaliadorcredito.service;

import io.github.cursodsouza.msavaliadorcredito.infra.client.ClienteResourceClient;
import io.github.cursodsouza.msavaliadorcredito.model.DadosCliente;
import io.github.cursodsouza.msavaliadorcredito.model.SituacaoCliente;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AvaliadorCreditoService {

    private final ClienteResourceClient clientesClient;
    public SituacaoCliente obterSituacaoCliente(String cpf) {
        ResponseEntity<DadosCliente> dadosClienteResponseEntity = clientesClient.findCpf(cpf);
        return SituacaoCliente
                .builder()
                .cliente(dadosClienteResponseEntity.getBody())
                .build();
    }
}
