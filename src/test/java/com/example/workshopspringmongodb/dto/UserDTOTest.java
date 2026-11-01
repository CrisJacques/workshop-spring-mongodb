package com.example.workshopspringmongodb.dto;

import com.example.workshopspringmongodb.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserDTOTest {

    @Test
    void constructorCopiesUserFields() {
        UserDTO dto = new UserDTO(new User("1", "Maria", "maria@example.com"));

        assertEquals("1", dto.getId());
        assertEquals("Maria", dto.getName());
        assertEquals("maria@example.com", dto.getEmail());
    }

    @Test
    void settersUpdateFields() {
        UserDTO dto = new UserDTO();
        dto.setId("2");
        dto.setName("Alex");
        dto.setEmail("alex@example.com");

        assertEquals("2", dto.getId());
        assertEquals("Alex", dto.getName());
        assertEquals("alex@example.com", dto.getEmail());
    }
}
