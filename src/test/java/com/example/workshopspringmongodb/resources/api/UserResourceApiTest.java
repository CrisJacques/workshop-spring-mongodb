package com.example.workshopspringmongodb.resources.api;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.*;

class UserResourceApiTest extends AbstractApiTest {

    @BeforeEach
    void setUp() {
        seedData();
    }

    @Test
    void shouldReturnAllUsers() {
        request()
                .get("/users")
                .then()
                .statusCode(200)
                .body("$", hasSize(2))
                .body("name", containsInAnyOrder("Maria Brown", "Alex Green"));
    }

    @Test
    void shouldReturnEmptyListWhenThereAreNoUsers() {
        clearDatabase();

        request()
                .get("/users")
                .then()
                .statusCode(200)
                .body("$", empty());
    }

    @Test
    void shouldReturnUserById() {
        request()
                .get("/users/{id}", maria.getId())
                .then()
                .statusCode(200)
                .body("id", equalTo(maria.getId()))
                .body("name", equalTo("Maria Brown"))
                .body("email", equalTo("maria@example.com"));
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() {
        request()
                .get("/users/{id}", "missing-user")
                .then()
                .statusCode(404)
                .body("status", equalTo(404))
                .body("path", equalTo("/users/missing-user"));
    }

    @Test
    void shouldCreateUserAndReturnItsLocation() {
        String location = request()
                .contentType(ContentType.JSON)
                .body("""
                        {"name":"New User","email":"new@example.com"}
                        """)
                .post("/users")
                .then()
                .statusCode(201)
                .extract()
                .header("Location");

        String id = location.substring(location.lastIndexOf('/') + 1);
        request()
                .get("/users/{id}", id)
                .then()
                .statusCode(200)
                .body("name", equalTo("New User"))
                .body("email", equalTo("new@example.com"));
    }

    @Test
    void shouldDeleteUser() {
        request()
                .delete("/users/{id}", alex.getId())
                .then()
                .statusCode(204);

        request()
                .get("/users/{id}", alex.getId())
                .then()
                .statusCode(404);
    }

    @Test
    void shouldReturnNotFoundWhenDeletingUnknownUser() {
        request()
                .delete("/users/{id}", "missing-user")
                .then()
                .statusCode(404);
    }

    @Test
    void shouldUpdateUser() {
        request()
                .contentType(ContentType.JSON)
                .body("""
                        {"name":"Maria Updated","email":"updated@example.com"}
                        """)
                .put("/users/{id}", maria.getId())
                .then()
                .statusCode(204);

        request()
                .get("/users/{id}", maria.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo("Maria Updated"))
                .body("email", equalTo("updated@example.com"));
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingUnknownUser() {
        request()
                .contentType(ContentType.JSON)
                .body("""
                        {"name":"Unknown","email":"unknown@example.com"}
                        """)
                .put("/users/{id}", "missing-user")
                .then()
                .statusCode(404);
    }

    @Test
    void shouldReturnPostsForUser() {
        request()
                .get("/users/{id}/posts", maria.getId())
                .then()
                .statusCode(200)
                .body("$", hasSize(2))
                .body("title", containsInAnyOrder("Viagem para São Paulo", "Bom dia"));
    }

    @Test
    void shouldReturnNotFoundWhenListingPostsForUnknownUser() {
        request()
                .get("/users/{id}/posts", "missing-user")
                .then()
                .statusCode(404);
    }
}
