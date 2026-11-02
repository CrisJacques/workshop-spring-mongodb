package com.example.workshopspringmongodb.resources.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class URLTest {

    @Test
    void decodeParamDecodesSpacesAndUtf8Characters() {
        assertEquals("bom dia", URL.decodeParam("bom%20dia"));
        assertEquals("coração", URL.decodeParam("cora%C3%A7%C3%A3o"));
    }

    @Test
    void decodeParamConvertsPlusToSpace() {
        assertEquals("bom dia", URL.decodeParam("bom+dia"));
    }

    @Test
    void convertDateParsesIsoDate() {
        assertEquals(LocalDate.of(2024, 2, 29),
                URL.convertDate("2024-02-29", LocalDate.of(2000, 1, 1)));
    }

    @Test
    void convertDateReturnsDefaultForEmptyInput() {
        LocalDate defaultDate = LocalDate.of(1970, 1, 1);

        assertEquals(defaultDate, URL.convertDate("", defaultDate));
    }

    @Test
    void convertDateReturnsDefaultForInvalidInput() {
        LocalDate defaultDate = LocalDate.of(1970, 1, 1);

        assertEquals(defaultDate, URL.convertDate("31/12/2024", defaultDate));
    }

    @Test
    void convertDateReturnsNullDefaultWhenInputIsInvalid() {
        assertEquals(null, URL.convertDate("invalid", null));
    }
}
