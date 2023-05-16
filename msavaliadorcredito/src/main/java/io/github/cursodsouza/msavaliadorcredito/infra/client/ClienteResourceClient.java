package io.github.cursodsouza.msavaliadorcredito.infra.client;

import io.github.cursodsouza.msavaliadorcredito.model.DadosCliente;
import io.github.cursodsouza.msavaliadorcredito.model.SituacaoCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "msclientes", path = "/clientes")
public interface ClienteResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<DadosCliente> findCpf(@RequestParam String cpf);
}
