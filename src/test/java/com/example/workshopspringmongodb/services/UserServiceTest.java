package com.example.workshopspringmongodb.services;

import com.example.workshopspringmongodb.domain.User;
import com.example.workshopspringmongodb.dto.UserDTO;
import com.example.workshopspringmongodb.repository.UserRepository;
import com.example.workshopspringmongodb.services.exception.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService service;

    @BeforeEach
    void setUp() {
        service = new UserService();
        ReflectionTestUtils.setField(service, "userRepository", userRepository);
    }

    @Test
    void findAllReturnsRepositoryResults() {
        List<User> users = List.of(new User("1", "Maria", "maria@example.com"));
        when(userRepository.findAll()).thenReturn(users);

        assertSame(users, service.findAll());
    }

    @Test
    void findByIdReturnsFoundUser() {
        User user = new User("1", "Maria", "maria@example.com");
        when(userRepository.findById("1")).thenReturn(Optional.of(user));

        assertSame(user, service.findById("1"));
    }

    @Test
    void findByIdThrowsWhenUserDoesNotExist() {
        when(userRepository.findById("missing")).thenReturn(Optional.empty());

        ObjectNotFoundException exception = assertThrows(
                ObjectNotFoundException.class, () -> service.findById("missing"));

        assertEquals("Objeto não encontrado", exception.getMessage());
    }

    @Test
    void fromDTOCopiesAllUserFields() {
        User user = service.fromDTO(new UserDTO(new User("1", "Maria", "maria@example.com")));

        assertEquals("1", user.getId());
        assertEquals("Maria", user.getName());
        assertEquals("maria@example.com", user.getEmail());
    }

    @Test
    void insertDelegatesToRepository() {
        User user = new User(null, "Maria", "maria@example.com");
        when(userRepository.save(user)).thenReturn(user);

        assertSame(user, service.insert(user));
        verify(userRepository).save(user);
    }

    @Test
    void deleteByIdDeletesAnExistingUser() {
        when(userRepository.findById("1")).thenReturn(Optional.of(new User("1", "Maria", "maria@example.com")));

        service.deleteById("1");

        verify(userRepository).deleteById("1");
    }

    @Test
    void deleteByIdDoesNotDeleteWhenUserIsMissing() {
        when(userRepository.findById("missing")).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.deleteById("missing"));

        verify(userRepository, never()).deleteById(anyString());
    }

    @Test
    void updateChangesNameAndEmailAndSavesExistingUser() {
        User existing = new User("1", "Old name", "old@example.com");
        User changes = new User("1", "New name", "new@example.com");
        when(userRepository.findById("1")).thenReturn(Optional.of(existing));
        when(userRepository.save(existing)).thenReturn(existing);

        User result = service.update(changes);

        assertSame(existing, result);
        assertEquals("New name", existing.getName());
        assertEquals("new@example.com", existing.getEmail());
        verify(userRepository).save(existing);
    }

    @Test
    void updateDoesNotSaveWhenUserIsMissing() {
        when(userRepository.findById("missing")).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class,
                () -> service.update(new User("missing", "Name", "email@example.com")));

        verify(userRepository, never()).save(any(User.class));
    }
}
