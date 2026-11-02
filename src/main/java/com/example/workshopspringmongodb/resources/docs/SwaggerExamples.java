package com.example.workshopspringmongodb.resources.docs;

public final class SwaggerExamples {

    public static final String USER_REQUEST = """
            {
              "name": "Maria Silva",
              "email": "maria.silva@example.com"
            }
            """;

    public static final String USER_RESPONSE = """
            {
              "id": "507f1f77bcf86cd799439011",
              "name": "Maria Silva",
              "email": "maria.silva@example.com"
            }
            """;

    public static final String USERS_RESPONSE = """
            [
              {
                "id": "507f1f77bcf86cd799439011",
                "name": "Maria Silva",
                "email": "maria.silva@example.com"
              }
            ]
            """;

    public static final String POST_RESPONSE = """
            {
              "id": "507f1f77bcf86cd799439022",
              "date": "2026-10-25",
              "title": "Novidades do Spring",
              "body": "Resumo das novidades da plataforma.",
              "author": {
                "id": "507f1f77bcf86cd799439011",
                "name": "Maria Silva"
              },
              "comments": [
                {
                  "text": "Excelente publicação!",
                  "date": "2026-10-26",
                  "author": {
                    "id": "507f1f77bcf86cd799439033",
                    "name": "Joao Souza"
                  }
                }
              ]
            }
            """;

    public static final String POSTS_RESPONSE = """
            [
              {
                "id": "507f1f77bcf86cd799439022",
                "date": "2026-10-25",
                "title": "Novidades do Spring",
                "body": "Resumo das novidades da plataforma.",
                "author": {
                  "id": "507f1f77bcf86cd799439011",
                  "name": "Maria Silva"
                },
                "comments": []
              }
            ]
            """;

    public static final String ERROR_RESPONSE = """
            {
              "timestamp": 1792951200000,
              "status": 404,
              "error": "Não encontrado",
              "message": "Objeto não encontrado",
              "path": "/users/507f1f77bcf86cd799439011"
            }
            """;

    private SwaggerExamples() {
    }
}
