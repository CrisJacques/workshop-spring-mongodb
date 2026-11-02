package com.example.workshopspringmongodb.domain;

import com.example.workshopspringmongodb.dto.AuthorDTO;
import com.example.workshopspringmongodb.dto.CommentDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document
@Getter
@Setter
@NoArgsConstructor
public class Post {

    @Id
    private String id;
    private LocalDate date;
    private String title;
    private String body;
    private AuthorDTO author;

    private List<CommentDTO> comments = new ArrayList<>();

    /**
     * Fazendo desta forma, o objeto AuthorDTO author irá ficar aninhado com o objeto Post no MongoDB.
     * Caso o desejo seja de que o Post tenha apenas a referência ao usuário, aí deve-se usar o @DBRef.
     *
     * Se uma entidade será persistida no banco aninhada a outra ou se será usada apenas uma referência
     * vai depender de cada caso, e é uma decisão técnica.
     */
    public Post(String id, LocalDate date, String title, String body, AuthorDTO author) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.body = body;
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return Objects.equals(id, post.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
