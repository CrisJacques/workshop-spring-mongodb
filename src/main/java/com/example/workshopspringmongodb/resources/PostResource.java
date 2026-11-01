package com.example.workshopspringmongodb.resources;

import com.example.workshopspringmongodb.domain.Post;
import com.example.workshopspringmongodb.dto.UserDTO;
import com.example.workshopspringmongodb.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/posts")
public class PostResource {
    @Autowired
    private PostService postService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(postService.findById(id));
    }

}
