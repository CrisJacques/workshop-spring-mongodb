package com.example.workshopspringmongodb.services;

import com.example.workshopspringmongodb.domain.User;
import com.example.workshopspringmongodb.dto.UserDTO;
import com.example.workshopspringmongodb.repository.UserRepository;
import com.example.workshopspringmongodb.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAll(){
        return userRepository.findAll();
    }

    public User findById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public User fromDTO(UserDTO dto) {
        return new User(dto.getId(), dto.getName(), dto.getEmail());
    }

    public User insert(User user) {
        return userRepository.save(user);
    }
}
