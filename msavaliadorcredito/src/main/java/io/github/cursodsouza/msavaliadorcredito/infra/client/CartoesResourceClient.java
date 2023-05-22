package io.github.cursodsouza.msavaliadorcredito.infra.client;

import io.github.cursodsouza.msavaliadorcredito.model.Cartao;
import io.github.cursodsouza.msavaliadorcredito.model.CartaoCliente;
import io.github.cursodsouza.msavaliadorcredito.model.DadosCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;

@FeignClient(value = "mscartoes", path = "/cartoes")
public interface CartoesResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<List<CartaoCliente>> getCartoesByCpf(@RequestParam String cpf);

    @GetMapping(params = "renda")
    public ResponseEntity<List<Cartao>> findByRendaMaiorOuIgual(@RequestParam BigDecimal renda);
}
