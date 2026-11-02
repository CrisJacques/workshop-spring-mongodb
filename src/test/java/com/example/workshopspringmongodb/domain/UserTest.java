package com.example.workshopspringmongodb.domain;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    @Test
    void defaultConstructorInitializesPosts() {
        assertNotNull(new User().getPosts());
        assertTrue(new User().getPosts().isEmpty());
    }

    @Test
    void constructorAndSettersExposeUserData() {
        User user = new User("1", "Maria", "maria@example.com");
        List<Post> posts = new ArrayList<>(List.of(new Post()));
        user.setId("2");
        user.setName("Alex");
        user.setEmail("alex@example.com");
        user.setPosts(posts);

        assertEquals("2", user.getId());
        assertEquals("Alex", user.getName());
        assertEquals("alex@example.com", user.getEmail());
        assertSame(posts, user.getPosts());
    }

    @Test
    void equalityAndHashCodeAreBasedOnId() {
        User first = new User("1", "Maria", "maria@example.com");
        User sameId = new User("1", "Different", "other@example.com");
        User otherId = new User("2", "Maria", "maria@example.com");

        assertEquals(first, sameId);
        assertEquals(first.hashCode(), sameId.hashCode());
        assertNotEquals(first, otherId);
    }

    @Test
    void toStringIncludesIdentityAndProfileFields() {
        String text = new User("1", "Maria", "maria@example.com").toString();

        assertTrue(text.contains("id='1'"));
        assertTrue(text.contains("name='Maria'"));
        assertTrue(text.contains("email='maria@example.com'"));
    }
}
