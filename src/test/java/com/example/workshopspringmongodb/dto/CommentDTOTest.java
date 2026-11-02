package com.example.workshopspringmongodb.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class CommentDTOTest {

    @Test
    void constructorSetsAllCommentFields() {
        LocalDate date = LocalDate.of(2024, 3, 10);
        AuthorDTO author = new AuthorDTO();
        CommentDTO comment = new CommentDTO("Nice post", date, author);

        assertEquals("Nice post", comment.getText());
        assertEquals(date, comment.getDate());
        assertSame(author, comment.getAuthor());
    }

    @Test
    void settersUpdateCommentFieldsAndAllowNulls() {
        CommentDTO comment = new CommentDTO();
        LocalDate date = LocalDate.of(2024, 3, 10);
        AuthorDTO author = new AuthorDTO();
        comment.setText("Comment");
        comment.setDate(date);
        comment.setAuthor(author);

        assertEquals("Comment", comment.getText());
        assertEquals(date, comment.getDate());
        assertSame(author, comment.getAuthor());

        comment.setText(null);
        comment.setDate(null);
        comment.setAuthor(null);
        assertNull(comment.getText());
        assertNull(comment.getDate());
        assertNull(comment.getAuthor());
    }
}
