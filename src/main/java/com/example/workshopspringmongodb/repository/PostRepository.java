package com.example.workshopspringmongodb.repository;

import com.example.workshopspringmongodb.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends MongoRepository<Post,String> {
    /**
     * É possível implementar os mais variados tipos de busca no banco de dados criando métodos que sigam um
     * padrão de nome do Spring Data MongoDB (ver documentação).
     *---
     * Basicamente, o nome do método segue o padrão: findBy + nome_do_atributo + estratégia_de_busca, sempre
     * respeitando o padrão camelCase.
     *---
     * Exemplos:
     * - findByFirstnameNull(): procura por registros cujo atributo firstName seja null;
     * - findByBirthdateAfter(Date date): procura por registros cujo atributo birthdate seja posterior à data informada;
     * - findByAgeLessThanEqual(int age): procura por registros cujo atributo age seja menor ou igual ao valor
     * informado;
     * - findByTitleContainingIgnoreCase(String text): procura por registros cujo atributo title contenha a string
     * informada, ignorando maiúsculas e minúsculas;
     * ---
     * Esses métodos são chamados de query methods.
     */
    List<Post> findByTitleContainingIgnoreCase(String text);

}
