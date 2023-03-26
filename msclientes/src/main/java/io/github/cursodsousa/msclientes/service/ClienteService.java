package io.github.cursodsousa.msclientes.service;

import io.github.cursodsousa.msclientes.dto.ClienteNewDTO;
import io.github.cursodsousa.msclientes.entity.Cliente;
import io.github.cursodsousa.msclientes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {
    private final ClienteRepository repository;

    @Transactional(readOnly = true)
    public Cliente findCpf(String cpf) throws Exception {
        Optional<Cliente> obj = repository.findByCpf(cpf);

        return obj.orElseThrow(() -> new Exception("Not Found"));
    }

    @Transactional
    public Cliente insert(ClienteNewDTO dto) {
        Cliente obj = dto.toModel();
        obj = repository.save(obj);

        return obj;
    }
}
