package io.github.cursodsousa.mscartoes.DTO;

import io.github.cursodsousa.mscartoes.entity.Cartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@NoArgsConstructor
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
