package com.example.workshopspringmongodb.resources.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

class PostResourceApiTest extends AbstractApiTest {

    @BeforeEach
    void setUp() {
        seedData();
    }

    @Test
    void shouldReturnPostById() {
        request()
                .get("/posts/{id}", tripPost.getId())
                .then()
                .statusCode(200)
                .body("id", equalTo(tripPost.getId()))
                .body("title", equalTo("Viagem para São Paulo"))
                .body("date", equalTo("2018-03-21"))
                .body("author.name", equalTo("Maria Brown"));
    }

    @Test
    void shouldReturnNotFoundWhenPostDoesNotExist() {
        request()
                .get("/posts/{id}", "missing-post")
                .then()
                .statusCode(404)
                .body("status", equalTo(404))
                .body("path", equalTo("/posts/missing-post"));
    }

    @Test
    void shouldFindPostsByTitleIgnoringCase() {
        request()
                .queryParam("text", "VIAGEM")
                .get("/posts/titlesearch")
                .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].id", equalTo(tripPost.getId()));
    }

    @Test
    void shouldReturnEmptyResultsWhenTitleDoesNotMatch() {
        request()
                .queryParam("text", "inexistente")
                .get("/posts/titlesearch")
                .then()
                .statusCode(200)
                .body("$", empty());
    }

    @Test
    void shouldSearchPostsByTextAndDateRange() {
        request()
                .queryParam("text", "feliz")
                .queryParam("minDate", "2018-03-20")
                .queryParam("maxDate", "2018-03-24")
                .get("/posts/fullsearch")
                .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].id", equalTo(morningPost.getId()));
    }

    @Test
    void shouldSearchPostCommentsAndRespectDateRange() {
        request()
                .queryParam("text", "Boa viagem")
                .queryParam("minDate", "2018-03-21")
                .queryParam("maxDate", "2018-03-21")
                .get("/posts/fullsearch")
                .then()
                .statusCode(200)
                .body("$", hasSize(1))
                .body("[0].id", equalTo(tripPost.getId()));
    }

    @Test
    void shouldReturnEmptyResultsWhenFullSearchHasNoMatch() {
        request()
                .queryParam("text", "inexistente")
                .queryParam("minDate", "2018-03-20")
                .queryParam("maxDate", "2018-03-24")
                .get("/posts/fullsearch")
                .then()
                .statusCode(200)
                .body("$", empty());
    }
}
