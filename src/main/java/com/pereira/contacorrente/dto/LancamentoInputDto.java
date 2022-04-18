package com.pereira.contacorrente.dto;

import lombok.Getter;

@Getter
public class LancamentoInputDto {

    private Integer valor;

    public LancamentoInputDto(Integer valor) {
        this.valor = valor;
    }

}