package io.github.cursodsousa.mscartoes.service;

import io.github.cursodsousa.mscartoes.DTO.CartaoDTO;
import io.github.cursodsousa.mscartoes.DTO.CartaoNewDTO;
import io.github.cursodsousa.mscartoes.DTO.clientecartoes.ClienteCartaoDTO;
import io.github.cursodsousa.mscartoes.entity.Cartao;
import io.github.cursodsousa.mscartoes.entity.ClienteCartao;
import io.github.cursodsousa.mscartoes.repository.CartaoRepository;
import io.github.cursodsousa.mscartoes.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteCartaoService {
    private final ClienteCartaoRepository repository;

    public List<ClienteCartaoDTO> listCartoesByCpf(String cpf) {

        return repository.findByCpf(cpf).stream().map(ClienteCartaoDTO::new).collect(Collectors.toList());
    }
}
