package io.github.cursodsousa.mscartoes.infra.mqueue;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.cursodsousa.mscartoes.entity.Cartao;
import io.github.cursodsousa.mscartoes.entity.ClienteCartao;
import io.github.cursodsousa.mscartoes.entity.DadosSolicitacaoEmissaoCartao;
import io.github.cursodsousa.mscartoes.repository.CartaoRepository;
import io.github.cursodsousa.mscartoes.repository.ClienteCartaoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmissaoCartaoSubscriber {
    private final CartaoRepository cartaoRepository;
    private final ClienteCartaoRepository clienteRepository;
    @RabbitListener(queues = "${mq.queues.emissao-cartoes}")
    public void receberSolicitacaoEmissao(@Payload String payload) {
        try {
            var mapper = new ObjectMapper();
            DadosSolicitacaoEmissaoCartao dados = mapper.readValue(payload, DadosSolicitacaoEmissaoCartao.class);
            Cartao cartao = cartaoRepository.findById(dados.getIdCartao()).orElseThrow();
            ClienteCartao clienteCartao = new ClienteCartao();
            clienteCartao.setCartao(cartao);
            clienteCartao.setCpf(dados.getCpf());
            clienteCartao.setLimite(dados.getLimiteLiberado());

            clienteRepository.save(clienteCartao);
        } catch (JsonProcessingException e) {
            log.error("Log emissão de cartões: {}", e);
        }
    }
}
