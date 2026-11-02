package com.example.workshopspringmongodb.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document // automaticamente o Spring Data MongoDB cria uma collection com nome user no banco
//@Document(collection="user") // Outra opção é definir explicitamente o nome da collection desejada
@Getter
@Setter
@NoArgsConstructor
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

    public User(String id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
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
