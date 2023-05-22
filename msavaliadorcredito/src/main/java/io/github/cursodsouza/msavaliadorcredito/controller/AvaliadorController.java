package io.github.cursodsouza.msavaliadorcredito.controller;

import io.github.cursodsouza.msavaliadorcredito.model.DadosAvaliacao;
import io.github.cursodsouza.msavaliadorcredito.model.RetornoAvaliacaoCliente;
import io.github.cursodsouza.msavaliadorcredito.model.SituacaoCliente;
import io.github.cursodsouza.msavaliadorcredito.service.AvaliadorCreditoService;
import io.github.cursodsouza.msavaliadorcredito.service.ex.DadosClienteNotFoundException;
import io.github.cursodsouza.msavaliadorcredito.service.ex.ErroComunicacaoMicroServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping
    public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dadosAvaliacao) throws DadosClienteNotFoundException, ErroComunicacaoMicroServiceException {
        RetornoAvaliacaoCliente retornoAvaliacaoCliente = new RetornoAvaliacaoCliente();
        try {
            retornoAvaliacaoCliente = avaliadorCreditoService.realizarAvaliacao(dadosAvaliacao.getCpf(), dadosAvaliacao.getRenda());
        } catch (DadosClienteNotFoundException e) {
            ResponseEntity.notFound();
        } catch (ErroComunicacaoMicroServiceException e) {
            ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
        return ResponseEntity.ok(retornoAvaliacaoCliente);
    }
}
