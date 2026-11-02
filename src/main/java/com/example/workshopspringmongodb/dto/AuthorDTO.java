package com.example.workshopspringmongodb.dto;

import com.example.workshopspringmongodb.domain.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * A ideia desta classe é projetar no banco de dados apenas os dados do autor que realmente interessam,
 * ao invés de persistir todos os dados de um usuário como autor de um post ou comentário.
 */
@Getter
@Setter
@NoArgsConstructor
public class AuthorDTO {

    private String id;
    private String name;

    public AuthorDTO(User obj) {
        this.id = obj.getId();
        this.name = obj.getName();
    }
}
