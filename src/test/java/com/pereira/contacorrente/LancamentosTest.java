package com.pereira.contacorrente;

import com.pereira.contacorrente.client.ContaCorrenteClient;
import com.pereira.contacorrente.dto.CedulaOutputDto;
import com.pereira.contacorrente.dto.LancamentoInputDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.pereira.contacorrente.evento.Consumer;
import com.pereira.contacorrente.evento.Producer;
import com.pereira.contacorrente.util.Util;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.apache.http.HttpStatus.*;
import static org.assertj.core.api.Assertions.assertThat;

class LancamentosTest {

    private static ContaCorrenteClient contaCorrenteClient;

    @BeforeAll
    static void beforeTest() {
        contaCorrenteClient = new ContaCorrenteClient();
    }

    @Test
    @DisplayName("Deve retornar not found para conta sem lancamentos")
    void shouldReturnNotFoundAccountWithoutMoviments() {
        String accountWithoutMoviments = "16";
        Response response = contaCorrenteClient.getLancamentos(accountWithoutMoviments);

        assertThat(response.getStatusCode()).isEqualTo(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Deve sacar 30 reais da conta em cédulas")
    void shouldTakeOutMoney() throws JsonProcessingException {
        Response response = contaCorrenteClient.postLancamento("7", new LancamentoInputDto(30));
        List<CedulaOutputDto> listMoney = Util.convertToListCedulaOutputDto(response.getBody().print());

        assertThat(response.getStatusCode()).isEqualTo(SC_CREATED);
        Assert.assertEquals(10, listMoney.get(0).getCedula().intValue());
        Assert.assertEquals(1, listMoney.get(0).getQuantidade().intValue());
        Assert.assertEquals(20, listMoney.get(1).getCedula().intValue());
        Assert.assertEquals(1, listMoney.get(1).getQuantidade().intValue());
    }

    @Test
    @DisplayName("Deve enviar nome para o tópico do kafka")
    void shouldSendMessage() throws ExecutionException, InterruptedException {
        String nome = "Alcemar";
        Producer.sendMesssage(nome);
        List<String> mensagens = Consumer.consumer();
        Assert.assertTrue(Util.procurarStringLista(mensagens, nome));
    }

}