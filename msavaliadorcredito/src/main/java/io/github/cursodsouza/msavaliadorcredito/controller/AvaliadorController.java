package io.github.cursodsouza.msavaliadorcredito.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("avaliacoes-credito")
public class AvaliadorController {
    @GetMapping
    public String status() {
        return "ok";
    }
}
