package com.example.workshopspringmongodb.config;

import com.example.workshopspringmongodb.domain.Post;
import com.example.workshopspringmongodb.domain.User;
import com.example.workshopspringmongodb.dto.CommentDTO;
import com.example.workshopspringmongodb.repository.PostRepository;
import com.example.workshopspringmongodb.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InstantiationTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PostRepository postRepository;

    private Instantiation instantiation;

    @BeforeEach
    void setUp() {
        instantiation = new Instantiation();
        ReflectionTestUtils.setField(instantiation, "userRepository", userRepository);
        ReflectionTestUtils.setField(instantiation, "postRepository", postRepository);
    }

    @Test
    void runClearsRepositoriesAndCreatesConnectedSampleData() throws Exception {
        instantiation.run();

        InOrder order = inOrder(userRepository, postRepository);
        order.verify(userRepository).deleteAll();
        order.verify(postRepository).deleteAll();
        order.verify(userRepository).saveAll(anyList());

        @SuppressWarnings("unchecked")
        ArgumentCaptor<Iterable<Post>> postsCaptor = ArgumentCaptor.forClass(Iterable.class);
        verify(postRepository).saveAll(postsCaptor.capture());
        List<Post> posts = toList(postsCaptor.getValue());
        assertEquals(2, posts.size());
        assertEquals("Partiu viagem", posts.get(0).getTitle());
        assertEquals("Bom dia", posts.get(1).getTitle());
        assertEquals(2, posts.get(0).getComments().size());
        assertEquals(1, posts.get(1).getComments().size());
        assertEquals("Maria Brown", posts.get(0).getAuthor().getName());
        assertEquals("Alex Green", posts.get(0).getComments().get(0).getAuthor().getName());

        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userRepository).save(userCaptor.capture());
        assertEquals("Maria Brown", userCaptor.getValue().getName());
        assertEquals(posts, userCaptor.getValue().getPosts());
    }

    private List<Post> toList(Iterable<Post> posts) {
        java.util.ArrayList<Post> result = new java.util.ArrayList<>();
        posts.forEach(result::add);
        return result;
    }
}
