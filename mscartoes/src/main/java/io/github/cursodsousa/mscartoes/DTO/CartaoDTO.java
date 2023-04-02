package io.github.cursodsousa.mscartoes.DTO;

import io.github.cursodsousa.mscartoes.entity.Cartao;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class CartaoDTO {
    private String nome;
    private String bandeira;
    private BigDecimal renda;
    private BigDecimal limiteBasico;


    public CartaoDTO(Cartao obj) {
        this.nome = obj.getNome();
        this.bandeira = obj.getBandeira().getDesc();
        renda = obj.getRenda();
        limiteBasico = obj.getLimiteBasico();
    }
}
