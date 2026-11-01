package com.example.workshopspringmongodb.resources.util;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class URL {

    /**
     * Pode acontecer de parâmetros da URL usados nas consultas tenham espaços ou caracteres especiais. Nesse
     * caso, é preciso codificar esses parâmetros para não dar erro na requisição. Porém, para poder processar
     * esses parâmetros no backend, será preciso decodificá-los no momento do recebimento da request.
     * ---
     * Para codificar parâmetros de URL, uma opção famosa é o encodeURI(text) do Javascript, que conseguimos
     * usar pelo console do navegador. Por exemplo: encodeURI("bom dia") = "bom%20dia"
     * ---
     * A ideia do método abaixo é transformar o "bom%20dia" de volta em "bom dia".
     */
    public static String decodeParam(String text) {
        return URLDecoder.decode(text, StandardCharsets.UTF_8);
    }

    public static LocalDate convertDate(String textDate, LocalDate defaultValue) {
        try {
            DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return LocalDate.parse(textDate, dtFormatter);
        } catch (Exception e) {
            return defaultValue;
        }
    }



}
