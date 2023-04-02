package io.github.cursodsousa.mscartoes.service;

import io.github.cursodsousa.mscartoes.DTO.CartaoDTO;
import io.github.cursodsousa.mscartoes.DTO.CartaoNewDTO;
import io.github.cursodsousa.mscartoes.entity.Cartao;
import io.github.cursodsousa.mscartoes.repository.CartaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartaoService {
    private final CartaoRepository repository;

    public List<CartaoDTO> listByRenda(BigDecimal renda){
        List<Cartao> cartoes = repository.findByRendaLessThanEqual(renda);

        return cartoes.stream().map(CartaoDTO::new).collect(Collectors.toList());
    }

    public CartaoDTO insert(CartaoNewDTO dto) {
        Cartao obj = dto.toModel();
        obj = repository.save(obj);

        return new CartaoDTO(obj);
    }
}
