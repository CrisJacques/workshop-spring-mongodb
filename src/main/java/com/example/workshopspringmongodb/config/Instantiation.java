package com.example.workshopspringmongodb.config;

import com.example.workshopspringmongodb.domain.Post;
import com.example.workshopspringmongodb.domain.User;
import com.example.workshopspringmongodb.dto.AuthorDTO;
import com.example.workshopspringmongodb.dto.CommentDTO;
import com.example.workshopspringmongodb.repository.PostRepository;
import com.example.workshopspringmongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Configuration
public class Instantiation implements CommandLineRunner {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    DateTimeFormatter dtFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    public void run(String... args) throws Exception {
        userRepository.deleteAll();
        postRepository.deleteAll();

        User maria = new User(null, "Maria Brown", "maria@gmail.com");
        User alex = new User(null, "Alex Green", "alex@gmail.com");
        User bob = new User(null, "Bob Grey", "bob@gmail.com");
        userRepository.saveAll(Arrays.asList(maria, alex, bob)); // Tem que salvar os users primeiro, senão
        // quando for salvar os posts os ids dos usuários estarão null porque eles ainda não foram persistidos
        // no banco de dados.

        Post post1 = new Post(null,
                LocalDate.parse("21/03/2018", dtFormatter),
                "Partiu viagem",
                "Vou viajar para São Paulo. Abraços!",
                new AuthorDTO(maria));
        Post post2 = new Post(null,
                LocalDate.parse("23/03/2018", dtFormatter),
                "Bom dia",
                "Acordei feliz hoje!",
                new AuthorDTO(maria));

        CommentDTO c1 = new CommentDTO("Boa viagem mano!", LocalDate.parse("21/03/2018", dtFormatter), new AuthorDTO(alex));
        CommentDTO c2 = new CommentDTO("Aproveite", LocalDate.parse("22/03/2018", dtFormatter), new AuthorDTO(bob));
        CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", LocalDate.parse("23/03/2018", dtFormatter), new AuthorDTO(alex));

        post1.getComments().addAll(Arrays.asList(c1, c2));
        post2.getComments().add(c3);

        postRepository.saveAll(Arrays.asList(post1, post2));

        maria.getPosts().addAll(Arrays.asList(post1, post2));
        userRepository.save(maria);
    }
}
