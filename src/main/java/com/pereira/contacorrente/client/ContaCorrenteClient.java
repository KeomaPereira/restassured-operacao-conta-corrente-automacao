package com.pereira.contacorrente.client;

import static io.restassured.RestAssured.given;

import com.pereira.contacorrente.dto.LancamentoInputDto;
import com.pereira.contacorrente.util.Util;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class ContaCorrenteClient {

    public Response getLancamentos(String account) {
        return
            given().
                pathParam("conta", account).
            when().
                get("/conta-corrente/lancamentos/saques/{conta}").
            then().
                extract().
                    response();
    }

    public Response postLancamento(String account, LancamentoInputDto dto) throws JsonProcessingException {
        return
            given().
                pathParam("conta", account).
                contentType(ContentType.JSON).
                body(Util.toJson(dto)).
            when().
                post("/conta-corrente/lancamentos/saques/{conta}").
            then().
                extract().
                response();
    }

}
