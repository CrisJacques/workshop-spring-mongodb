package com.example.workshopspringmongodb.dto;

import com.example.workshopspringmongodb.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthorDTOTest {

    @Test
    void constructorCopiesOnlyAuthorIdentityAndName() {
        AuthorDTO dto = new AuthorDTO(new User("1", "Maria", "maria@example.com"));

        assertEquals("1", dto.getId());
        assertEquals("Maria", dto.getName());
    }

    @Test
    void settersUpdateFields() {
        AuthorDTO dto = new AuthorDTO();
        dto.setId("2");
        dto.setName("Alex");

        assertEquals("2", dto.getId());
        assertEquals("Alex", dto.getName());
    }
}
