package com.example.workshopspringmongodb.dto;

import java.time.LocalDate;

/**
 * Como um comentário é um objeto extremamente simples, se optou por não criar uma entidade para ele, e sim
 * um DTO. Além disso, ele estará sempre dentro de um objeto Post.
 *
 * Se o comentário fosse um objeto mais complexo (tivesse mais atributos e comportamentos), talvez valeria
 * a pena transformaá-lo em uma entidade, mas isso varia caso a caso.
 */
public class CommentDTO {

    private String text;
    private LocalDate date;
    private AuthorDTO author;

    public CommentDTO() {

    }

    public CommentDTO(String text, LocalDate date, AuthorDTO author) {
        this.text = text;
        this.date = date;
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public AuthorDTO getAuthor() {
        return author;
    }

    public void setAuthor(AuthorDTO author) {
        this.author = author;
    }
}
