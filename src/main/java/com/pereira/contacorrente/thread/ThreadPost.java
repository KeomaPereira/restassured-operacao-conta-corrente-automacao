package com.pereira.contacorrente.thread;

import com.pereira.contacorrente.client.ContaCorrenteClient;
import com.pereira.contacorrente.dto.CedulaOutputDto;
import com.pereira.contacorrente.dto.LancamentoInputDto;
import com.pereira.contacorrente.util.Util;
import io.restassured.response.Response;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;


public class ThreadPost implements Runnable{

    private Integer count = 0;
    private Integer timeToRun = 0;

    public ThreadPost(Integer count, Integer timeToRun) {
        this.count = count;
        this.timeToRun = timeToRun;
    }

    public void run() {
        System.out.println("Thread:" + count + " iniciada");
        try {
            for (int i = 0; i < timeToRun; i++) {
                ContaCorrenteClient contaCorrenteClient = new ContaCorrenteClient();

                Response response = contaCorrenteClient.postLancamento("7", new LancamentoInputDto(30));
                List<CedulaOutputDto> listMoney = Util.convertToListCedulaOutputDto(response.getBody().print());

                assert(response.getStatusCode() == SC_CREATED);
                assert(10 == listMoney.get(0).getCedula().intValue());
                assert(1 == listMoney.get(0).getQuantidade().intValue());
                assert(20 == listMoney.get(1).getCedula().intValue());
                assert(1 == listMoney.get(1).getQuantidade().intValue());

                System.out.println("Thread:" + count + ". Requisição: " + i + " enviada com sucesso");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
