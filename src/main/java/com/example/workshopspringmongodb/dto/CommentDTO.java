package com.example.workshopspringmongodb.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Como um comentário é um objeto extremamente simples, se optou por não criar uma entidade para ele, e sim
 * um DTO. Além disso, ele estará sempre dentro de um objeto Post.
 *
 * Se o comentário fosse um objeto mais complexo (tivesse mais atributos e comportamentos), talvez valeria
 * a pena transformaá-lo em uma entidade, mas isso varia caso a caso.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {

    private String text;
    private LocalDate date;
    private AuthorDTO author;
}
