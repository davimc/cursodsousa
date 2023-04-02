package io.github.cursodsousa.mscartoes.DTO;

import io.github.cursodsousa.mscartoes.entity.Cartao;
import io.github.cursodsousa.mscartoes.entity.enums.Bandeira;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
public class CartaoNewDTO {
    private String nome;
    private String bandeira;
    private BigDecimal renda;
    private BigDecimal limiteBasico;

    public Cartao toModel() {
        Cartao obj = new Cartao();
        obj.setNome(nome);
        obj.setRenda(renda);
        obj.setLimiteBasico(limiteBasico);
        obj.setBandeira(Bandeira.toEnum(bandeira));

        return obj;
    }
}
