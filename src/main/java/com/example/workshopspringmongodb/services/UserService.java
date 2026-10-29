package com.example.workshopspringmongodb.services;

import com.example.workshopspringmongodb.domain.User;
import com.example.workshopspringmongodb.repository.UserRepository;
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
}
