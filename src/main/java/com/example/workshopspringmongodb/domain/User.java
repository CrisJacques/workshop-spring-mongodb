package com.example.workshopspringmongodb.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document // automaticamente o Spring Data MongoDB cria uma collection com nome user no banco
//@Document(collection="user") // Outra opção é definir explicitamente o nome da collection desejada
public class User {

    @Id
    private String id;
    private String name;
    private String email;

    @DBRef(lazy = true) // Neste caso, os Posts nâo vão ficar aninhados dentro do usuário, eles serão referenciados.
    // Além disso, o lazy = true evita que todos os posts do usuário sejam carregados junto com ele toda vez que
    // buscamos pelo usuário no banco de dados, evitando tráfego de rede desnecessário. Ou seja, nesse caso os posts
    // só serão carregados se buscarmos eles explicitamente.
    private List<Post> posts = new ArrayList<>();

    public User() {
    }

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
