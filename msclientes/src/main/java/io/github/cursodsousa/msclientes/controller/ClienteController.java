package io.github.cursodsousa.msclientes.controller;

import io.github.cursodsousa.msclientes.dto.ClienteNewDTO;
import io.github.cursodsousa.msclientes.entity.Cliente;
import io.github.cursodsousa.msclientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService service;

    @GetMapping
    public String teste() {
        return "OK";
    }
    @GetMapping(value = "/cpf")
    public ResponseEntity findCpf(@RequestParam String cpf) {
        try {
            return ResponseEntity.ok().body(service.findCpf(cpf));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping
    public ResponseEntity<Cliente> insert(@RequestBody ClienteNewDTO dto) {
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                        .path("/id")
                                .build().toUri();

        return ResponseEntity.created(uri).body(service.insert(dto));
    }
}
