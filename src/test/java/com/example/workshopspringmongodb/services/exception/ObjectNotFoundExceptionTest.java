package com.example.workshopspringmongodb.services.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ObjectNotFoundExceptionTest {

    @Test
    void preservesProvidedMessage() {
        ObjectNotFoundException exception = new ObjectNotFoundException("Objeto não encontrado");

        assertEquals("Objeto não encontrado", exception.getMessage());
    }
}
