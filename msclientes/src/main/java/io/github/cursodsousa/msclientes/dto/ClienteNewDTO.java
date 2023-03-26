package io.github.cursodsousa.msclientes.dto;

import io.github.cursodsousa.msclientes.entity.Cliente;
import lombok.Data;

@Data
public class ClienteNewDTO {

    private String cpf;
    private String name;
    private Integer idade;

    public Cliente toModel() {
        return new Cliente(name, cpf, idade);
    }

}
