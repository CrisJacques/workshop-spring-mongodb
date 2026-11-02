package com.example.workshopspringmongodb.resources.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StandardErrorTest {

    @Test
    void constructorAndSettersExposeErrorDetails() {
        StandardError error = new StandardError(1L, 404, "Not found", "Missing", "/users/1");

        assertEquals(1L, error.getTimestamp());
        assertEquals(404, error.getStatus());
        assertEquals("Not found", error.getError());
        assertEquals("Missing", error.getMessage());
        assertEquals("/users/1", error.getPath());

        error.setTimestamp(2L);
        error.setStatus(500);
        error.setError("Internal error");
        error.setMessage("Failure");
        error.setPath("/posts");
        assertEquals(2L, error.getTimestamp());
        assertEquals(500, error.getStatus());
        assertEquals("Internal error", error.getError());
        assertEquals("Failure", error.getMessage());
        assertEquals("/posts", error.getPath());
    }
}
