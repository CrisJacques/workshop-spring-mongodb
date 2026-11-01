package com.example.workshopspringmongodb.repository;

import com.example.workshopspringmongodb.domain.Post;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    /**
     * O @Query permite executar consultas usando a sintaxe do MongoDB. Na documentação do MongoDB tem mais informações
     * sobre as mais variadas formas de fazer consultas.
     *
     * No caso abaixo, a consulta a ser feita leva em conta o atributo title, cujo regex será lido da primeira variável
     * informada na chamada do método (?0 indica isso), ignorando maíusculas e minúsculas (o $options: 'i' indica isso).
     */
    @Query("{ 'title': { $regex: ?0, $options: 'i' } }")
    List<Post> searchTitle(String text);

    /**
     * Este método busca por posts que correspondam aos seguintes critérios de busca (todos devem ser satisfeitos):
     * - Data do post está entre minDate e maxDate;
     * - Post contém a string text no título, corpo ou comentários.
     * ----
     * As consultas mostradas no @Query abaixo foram extraídas da própria documentação do MongoDB
     * ----
     * Significado de algumas expressões usadas abaixo:
     * - gte: greater than or equal (maior ou igual);
     * - lte: less than or equal (menor ou igual);
     * - ?0, ?1, ?2: indicam, respectivamente, o primeiro, segundo e terceiro argumentos da chamada da função.
     * ----
     * - Como o atributo comments é uma lista de CommentsDTO, o que realmente nos interessa buscar é o conteúdo do seu
     * atributo text, por isso usamos comments.text na Query abaixo.
     */
    @Query("{ $and: [ " +
            "{ date: { $gte: ?1 } }, " +
            "{ date: { $lte: ?2 } }, " +
            "{ $or: [ " +
            "{ 'title': { $regex: ?0, $options: 'i' } }, " +
            "{ 'body': { $regex: ?0, $options: 'i' } }, " +
            "{ 'comments.text': { $regex: ?0, $options: 'i' } } " +
            "] } " +
            "] }")
    List<Post> fullSearch(String text, LocalDate minDate, LocalDate maxDate);

}
