package com.example.workshopspringmongodb.resources;

import com.example.workshopspringmongodb.domain.Post;
import com.example.workshopspringmongodb.dto.UserDTO;
import com.example.workshopspringmongodb.resources.util.URL;
import com.example.workshopspringmongodb.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value="/posts")
public class PostResource {
    @Autowired
    private PostService postService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Post> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(postService.findById(id));
    }

    /**
     * @RequestParam é usado para query params da URL. O value diz para o Spring qual é o query param que deve ser lido
     * e o defaultValue diz qual valor deve ser assumido caso o query param não seja informado na request.
     */
    @GetMapping(value = "/titlesearch")
    public ResponseEntity<List<Post>> findByTitle(@RequestParam(value="text", defaultValue = "") String text) {
        return ResponseEntity.ok().body(postService.findByTitle(URL.decodeParam(text)));
    }

}
