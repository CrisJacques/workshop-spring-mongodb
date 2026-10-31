package com.example.workshopspringmongodb.dto;

import com.example.workshopspringmongodb.domain.User;

/**
 * A ideia desta classe é projetar no banco de dados apenas os dados do autor que realmente interessam,
 * ao invés de persistir todos os dados de um usuário como autor de um post ou comentário.
 */
public class AuthorDTO {

    private String id;
    private String name;

    public AuthorDTO() {

    }

    public AuthorDTO(User obj) {
        this.id = obj.getId();
        this.name = obj.getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
