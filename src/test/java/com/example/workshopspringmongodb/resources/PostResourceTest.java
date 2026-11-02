package com.example.workshopspringmongodb.resources;

import com.example.workshopspringmongodb.domain.Post;
import com.example.workshopspringmongodb.services.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostResourceTest {

    private PostService postService;
    private PostResource resource;

    @BeforeEach
    void setUp() {
        postService = mock(PostService.class);
        resource = new PostResource();
        ReflectionTestUtils.setField(resource, "postService", postService);
    }

    @Test
    void findByIdReturnsPost() {
        Post post = new Post();
        when(postService.findById("1")).thenReturn(post);

        var response = resource.findById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(post, response.getBody());
    }

    @Test
    void findByTitleDecodesSearchText() {
        List<Post> posts = List.of(new Post());
        when(postService.findByTitle("bom dia")).thenReturn(posts);

        var response = resource.findByTitle("bom%20dia");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(posts, response.getBody());
        verify(postService).findByTitle("bom dia");
    }

    @Test
    void findByTitleAllowsEmptySearch() {
        resource.findByTitle("");

        verify(postService).findByTitle("");
    }

    @Test
    void fullSearchParsesTextAndDates() {
        List<Post> posts = List.of(new Post());
        LocalDate minDate = LocalDate.of(2020, 1, 1);
        LocalDate maxDate = LocalDate.of(2020, 12, 31);
        when(postService.fullSearch("bom dia", minDate, maxDate)).thenReturn(posts);

        var response = resource.fullSearch("bom%20dia", "2020-01-01", "2020-12-31");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(posts, response.getBody());
        verify(postService).fullSearch("bom dia", minDate, maxDate);
    }

    @Test
    void fullSearchUsesDefaultsForMissingOrInvalidDates() {
        resource.fullSearch("", "", "not-a-date");

        verify(postService).fullSearch("", LocalDate.of(1970, 1, 1), LocalDate.now());
    }
}
