package com.example.workshopspringmongodb.services;

import com.example.workshopspringmongodb.domain.Post;
import com.example.workshopspringmongodb.repository.PostRepository;
import com.example.workshopspringmongodb.services.exception.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post findById(String id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));
    }

    public List<Post> findByTitle(String text) {
        return postRepository.findByTitleContainingIgnoreCase(text);
    }

}
