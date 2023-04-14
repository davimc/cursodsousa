package io.github.cursodsousa.mscartoes.controller;

import io.github.cursodsousa.mscartoes.DTO.CartaoDTO;
import io.github.cursodsousa.mscartoes.DTO.CartaoNewDTO;
import io.github.cursodsousa.mscartoes.DTO.clientecartoes.ClienteCartaoDTO;
import io.github.cursodsousa.mscartoes.service.CartaoService;
import io.github.cursodsousa.mscartoes.service.ClienteCartaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("cartoes")
@RequiredArgsConstructor
public class CartaoController {

    private final CartaoService cartaoService;
    private final ClienteCartaoService clienteCartaoService;
    @GetMapping
    public ResponseEntity<String> status() {
        return ResponseEntity.ok().body("OK");
    }

    @GetMapping(params = "renda")
    public ResponseEntity<List<CartaoDTO>> findByRendaMaiorOuIgual(@RequestParam BigDecimal renda) {
        return ResponseEntity.ok().body(cartaoService.listByRenda(renda));
    }

    @GetMapping(params = "cpf")
    public ResponseEntity<List<ClienteCartaoDTO>> getCartoesByCpf(@RequestParam String cpf) {
        return ResponseEntity.ok().body(clienteCartaoService.listCartoesByCpf(cpf));
    }

    @PostMapping
    public ResponseEntity<CartaoDTO> insert (@RequestBody CartaoNewDTO dto) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/id")
                .build().toUri();

        return ResponseEntity.created(uri).body(cartaoService.insert(dto));
    }

}
