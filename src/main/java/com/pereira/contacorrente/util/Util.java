package com.pereira.contacorrente.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pereira.contacorrente.dto.CedulaOutputDto;

import java.util.List;

public class Util {

    public static String toJson(Object object) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(object);
    }

    public static List<CedulaOutputDto> convertToListCedulaOutputDto(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, new TypeToken<List<CedulaOutputDto>>(){}.getType());
    }

    public static boolean procurarStringLista(List<String> lista, String nome) {
        for (String element : lista) {
            if (element.contains(nome)) {
                return true;
            }
        }
        return false;
    }

}