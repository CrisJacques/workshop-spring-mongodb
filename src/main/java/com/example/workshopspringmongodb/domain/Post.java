package com.example.workshopspringmongodb.domain;

import com.example.workshopspringmongodb.dto.AuthorDTO;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Objects;

@Document
public class Post {

    @Id
    private String id;
    private LocalDate date;
    private String title;
    private String body;
    private AuthorDTO author;

    public Post() {

    }

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
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
