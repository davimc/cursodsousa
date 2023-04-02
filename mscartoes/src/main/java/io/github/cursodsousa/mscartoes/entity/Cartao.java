package io.github.cursodsousa.mscartoes.entity;

import io.github.cursodsousa.mscartoes.entity.enums.Bandeira;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
public class Cartao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Enumerated(EnumType.ORDINAL)
    private Bandeira bandeira;
    private BigDecimal renda;
    private BigDecimal limiteBasico;

    public Cartao(String name, Bandeira bandeira, BigDecimal renda, BigDecimal limiteBasico) {
        this.name = name;
        this.bandeira = bandeira;
        this.renda = renda;
        this.limiteBasico = limiteBasico;
    }
}
