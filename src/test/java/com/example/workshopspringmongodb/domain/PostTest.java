package com.example.workshopspringmongodb.domain;

import com.example.workshopspringmongodb.dto.AuthorDTO;
import com.example.workshopspringmongodb.dto.CommentDTO;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    void defaultConstructorInitializesComments() {
        Post post = new Post();

        assertNotNull(post.getComments());
        assertTrue(post.getComments().isEmpty());
    }

    @Test
    void constructorAndSettersExposePostData() {
        AuthorDTO author = new AuthorDTO(new User("u1", "Maria", "maria@example.com"));
        Post post = new Post("p1", LocalDate.of(2024, 1, 2), "Title", "Body", author);
        List<CommentDTO> comments = new ArrayList<>(List.of(new CommentDTO()));
        post.setId("p2");
        post.setDate(LocalDate.of(2024, 2, 3));
        post.setTitle("Changed");
        post.setBody("New body");
        post.setAuthor(null);
        post.setComments(comments);

        assertEquals("p2", post.getId());
        assertEquals(LocalDate.of(2024, 2, 3), post.getDate());
        assertEquals("Changed", post.getTitle());
        assertEquals("New body", post.getBody());
        assertNull(post.getAuthor());
        assertSame(comments, post.getComments());
    }

    @Test
    void equalityAndHashCodeAreBasedOnId() {
        Post first = new Post();
        first.setId("1");
        Post sameId = new Post();
        sameId.setId("1");
        Post otherId = new Post();
        otherId.setId("2");

        assertEquals(first, sameId);
        assertEquals(first.hashCode(), sameId.hashCode());
        assertNotEquals(first, otherId);
        assertNotEquals(first, null);
        assertNotEquals(first, "1");
    }

    @Test
    void postsWithNullIdsCompareEqual() {
        assertEquals(new Post(), new Post());
    }
}
