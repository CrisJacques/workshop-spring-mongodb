package com.example.workshopspringmongodb.services;

import com.example.workshopspringmongodb.domain.Post;
import com.example.workshopspringmongodb.repository.PostRepository;
import com.example.workshopspringmongodb.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    private PostService service;

    @BeforeEach
    void setUp() {
        service = new PostService();
        ReflectionTestUtils.setField(service, "postRepository", postRepository);
    }

    @Test
    void findByIdReturnsFoundPost() {
        Post post = new Post();
        when(postRepository.findById("1")).thenReturn(Optional.of(post));

        assertSame(post, service.findById("1"));
    }

    @Test
    void findByIdThrowsWhenPostDoesNotExist() {
        when(postRepository.findById("missing")).thenReturn(Optional.empty());

        ObjectNotFoundException exception = assertThrows(
                ObjectNotFoundException.class, () -> service.findById("missing"));

        assertEquals("Objeto não encontrado", exception.getMessage());
    }

    @Test
    void findByTitleDelegatesSearchText() {
        List<Post> posts = List.of(new Post());
        when(postRepository.searchTitle("trip")).thenReturn(posts);

        assertSame(posts, service.findByTitle("trip"));
        verify(postRepository).searchTitle("trip");
    }

    @Test
    void fullSearchDelegatesTextAndDateRange() {
        List<Post> posts = List.of(new Post());
        LocalDate minDate = LocalDate.of(2020, 1, 1);
        LocalDate maxDate = LocalDate.of(2020, 12, 31);
        when(postRepository.fullSearch("trip", minDate, maxDate)).thenReturn(posts);

        assertSame(posts, service.fullSearch("trip", minDate, maxDate));
        verify(postRepository).fullSearch("trip", minDate, maxDate);
    }
}
