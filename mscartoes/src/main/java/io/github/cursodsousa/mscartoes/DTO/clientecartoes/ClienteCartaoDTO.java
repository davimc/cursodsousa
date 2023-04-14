package io.github.cursodsousa.mscartoes.DTO.clientecartoes;

import io.github.cursodsousa.mscartoes.entity.ClienteCartao;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteCartaoDTO {
    private Long id;
    private String cpf;
    private BigDecimal limiteLiberado;

    public ClienteCartaoDTO(ClienteCartao obj) {
        id = obj.getId();
        cpf = obj.getCpf();
        limiteLiberado = obj.getLimite();
    }
}
