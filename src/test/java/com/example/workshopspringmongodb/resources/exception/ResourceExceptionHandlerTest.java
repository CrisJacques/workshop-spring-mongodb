package com.example.workshopspringmongodb.resources.exception;

import com.example.workshopspringmongodb.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;

import static org.junit.jupiter.api.Assertions.*;

class ResourceExceptionHandlerTest {

    @Test
    void objectNotFoundReturnsNotFoundErrorWithRequestPath() {
        ResourceExceptionHandler handler = new ResourceExceptionHandler();
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/users/unknown");
        long before = System.currentTimeMillis();

        var response = handler.objectNotFound(new ObjectNotFoundException("Objeto não encontrado"), request);

        long after = System.currentTimeMillis();
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getTimestamp() >= before);
        assertTrue(response.getBody().getTimestamp() <= after);
        assertEquals(404, response.getBody().getStatus());
        assertEquals("Não encontrado", response.getBody().getError());
        assertEquals("Objeto não encontrado", response.getBody().getMessage());
        assertEquals("/users/unknown", response.getBody().getPath());
    }
}
