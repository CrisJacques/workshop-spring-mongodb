package com.example.workshopspringmongodb.resources;

import com.example.workshopspringmongodb.domain.Post;
import com.example.workshopspringmongodb.domain.User;
import com.example.workshopspringmongodb.dto.UserDTO;
import com.example.workshopspringmongodb.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserResourceTest {

    private UserService userService;
    private UserResource resource;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        resource = new UserResource();
        ReflectionTestUtils.setField(resource, "userService", userService);
    }

    @AfterEach
    void clearRequestContext() {
        RequestContextHolder.resetRequestAttributes();
    }

    @Test
    void findAllReturnsUserDtos() {
        when(userService.findAll()).thenReturn(List.of(
                new User("1", "Maria", "maria@example.com"),
                new User("2", "Alex", "alex@example.com")));

        var response = resource.findAll();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Maria", response.getBody().get(0).getName());
        assertEquals("alex@example.com", response.getBody().get(1).getEmail());
    }

    @Test
    void findAllReturnsEmptyListWhenNoUsersExist() {
        when(userService.findAll()).thenReturn(List.of());

        assertTrue(resource.findAll().getBody().isEmpty());
    }

    @Test
    void findByIdReturnsUserDto() {
        when(userService.findById("1")).thenReturn(new User("1", "Maria", "maria@example.com"));

        var response = resource.findById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("1", response.getBody().getId());
        assertEquals("Maria", response.getBody().getName());
    }

    @Test
    void insertReturnsCreatedLocation() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRequestURI("/users");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        UserDTO input = new UserDTO();
        input.setName("Maria");
        input.setEmail("maria@example.com");
        when(userService.fromDTO(any(UserDTO.class))).thenReturn(new User(null, "Maria", "maria@example.com"));
        when(userService.insert(any(User.class))).thenReturn(new User("generated-id", "Maria", "maria@example.com"));

        var response = resource.insert(input);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("/users/generated-id", response.getHeaders().getLocation().getPath());
        verify(userService).insert(any(User.class));
    }

    @Test
    void deleteByIdReturnsNoContent() {
        var response = resource.deleteById("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService).deleteById("1");
    }

    @Test
    void updateUsesPathIdRatherThanDtoId() {
        UserDTO dto = new UserDTO();
        dto.setId("dto-id");
        dto.setName("Updated");
        dto.setEmail("updated@example.com");
        when(userService.fromDTO(dto)).thenReturn(new User("dto-id", "Updated", "updated@example.com"));

        var response = resource.update("path-id", dto);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService).update(argThat(user ->
                "path-id".equals(user.getId())
                        && "Updated".equals(user.getName())
                        && "updated@example.com".equals(user.getEmail())));
    }

    @Test
    void findPostsReturnsUserPosts() {
        Post post = new Post();
        User user = new User("1", "Maria", "maria@example.com");
        user.getPosts().add(post);
        when(userService.findById("1")).thenReturn(user);

        var response = resource.findPosts("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(List.of(post), response.getBody());
    }
}
