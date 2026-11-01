package com.example.workshopspringmongodb.resources.api;

import com.example.workshopspringmongodb.domain.Post;
import com.example.workshopspringmongodb.domain.User;
import com.example.workshopspringmongodb.dto.AuthorDTO;
import com.example.workshopspringmongodb.dto.CommentDTO;
import com.example.workshopspringmongodb.repository.PostRepository;
import com.example.workshopspringmongodb.repository.UserRepository;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.LocalDate;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = "spring.mongodb.uri=mongodb://localhost:27017/workshop_mongo_api_test"
)
abstract class AbstractApiTest {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PostRepository postRepository;

    @LocalServerPort
    protected int port;

    protected User maria;
    protected User alex;
    protected Post tripPost;
    protected Post morningPost;

    @AfterEach
    void cleanUp() {
        clearDatabase();
    }

    protected RequestSpecification request() {
        return RestAssured.given()
                .port(port)
                .accept("application/json");
    }

    protected void seedData() {
        userRepository.deleteAll();
        postRepository.deleteAll();

        maria = userRepository.save(new User(null, "Maria Brown", "maria@example.com"));
        alex = userRepository.save(new User(null, "Alex Green", "alex@example.com"));

        tripPost = new Post(null, LocalDate.of(2018, 3, 21), "Viagem para São Paulo",
                "Vou viajar para São Paulo.", new AuthorDTO(maria));
        tripPost.getComments().add(new CommentDTO(
                "Boa viagem!", LocalDate.of(2018, 3, 21), new AuthorDTO(alex)));

        morningPost = new Post(null, LocalDate.of(2018, 3, 23), "Bom dia",
                "Acordei feliz hoje!", new AuthorDTO(maria));
        morningPost.getComments().add(new CommentDTO(
                "Tenha um ótimo dia!", LocalDate.of(2018, 3, 23), new AuthorDTO(alex)));

        tripPost = postRepository.save(tripPost);
        morningPost = postRepository.save(morningPost);
        maria.getPosts().add(tripPost);
        maria.getPosts().add(morningPost);
        userRepository.save(maria);
    }

    protected void clearDatabase() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }
}
