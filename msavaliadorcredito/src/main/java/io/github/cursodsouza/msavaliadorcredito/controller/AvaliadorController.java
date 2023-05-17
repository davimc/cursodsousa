package io.github.cursodsouza.msavaliadorcredito.controller;

import io.github.cursodsouza.msavaliadorcredito.model.SituacaoCliente;
import io.github.cursodsouza.msavaliadorcredito.service.AvaliadorCreditoService;
import io.github.cursodsouza.msavaliadorcredito.service.ex.DadosClienteNotFoundException;
import io.github.cursodsouza.msavaliadorcredito.service.ex.ErroComunicacaoMicroServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("avaliacoes-credito")
@RequiredArgsConstructor
public class AvaliadorController {

    private final AvaliadorCreditoService avaliadorCreditoService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @GetMapping(value = "/situacao-cliente", params = "cpf")
    public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
        SituacaoCliente situacaoCliente = null;
        try {
            situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
        } catch (DadosClienteNotFoundException e) {
            ResponseEntity.notFound();
        } catch (ErroComunicacaoMicroServiceException e) {
            ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
        return ResponseEntity.ok(situacaoCliente);
    }
}
