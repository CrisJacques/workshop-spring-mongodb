# Workshop Spring Boot e MongoDB

[![Java](https://img.shields.io/badge/Java-25-ED8B00?logo=openjdk&logoColor=white)](https://www.java.com/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.2-6DB33F?logo=springboot&logoColor=white)](https://spring.io/projects/spring-boot)
[![MongoDB](https://img.shields.io/badge/MongoDB-Database-47A248?logo=mongodb&logoColor=white)](https://www.mongodb.com/)
[![Maven](https://img.shields.io/badge/Maven-wrapper-C71A36?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![JaCoCo](https://img.shields.io/badge/JaCoCo-97%25%20linhas-brightgreen)](https://www.jacoco.org/jacoco/)
[![Checkstyle](https://img.shields.io/badge/Checkstyle-Google%20Checks-blue)](https://checkstyle.org/)

API REST desenvolvida com Spring Boot e MongoDB para gerenciamento de usuários e consulta de publicações. O projeto demonstra organização em camadas, persistência orientada a documentos, documentação OpenAPI e testes automatizados de unidade e de API.

## Funcionalidades

- **Usuários:** listar todos, buscar por ID, cadastrar, atualizar e excluir.
- **Publicações de um usuário:** consultar os posts associados a um usuário.
- **Publicações:** buscar por ID, pesquisar por texto no título e filtrar por texto e intervalo de datas.
- **Tratamento de erros:** respostas padronizadas para recursos não encontrados.
- **Documentação interativa:** endpoints, parâmetros, respostas e exemplos disponíveis no Swagger UI.

## Tecnologias

| Tecnologia | Uso |
|---|---|
| Java 25 | Linguagem e plataforma de execução |
| Spring Boot 4.1.2 | Configuração e inicialização da aplicação |
| Spring Web MVC | Criação da API REST e dos endpoints HTTP |
| Spring Data MongoDB | Persistência e consultas ao banco de dados |
| MongoDB | Banco de dados NoSQL orientado a documentos |
| springdoc-openapi 3.2.0 | Geração da especificação OpenAPI 3 e interface Swagger UI |
| Lombok | Redução de código repetitivo nos modelos |
| Maven e Maven Wrapper | Gerenciamento de dependências e execução do build |
| Spring Boot Test | Suporte aos testes automatizados da aplicação |
| RestAssured 6.0.1 | Testes de integração dos endpoints HTTP |
| JaCoCo 0.8.14 | Medição e verificação da cobertura de testes |
| Maven Checkstyle Plugin 3.6.0 / Checkstyle 14.4.0 | Verificação de estilo com as regras Google Checks |
| Maven Compiler, Surefire e Failsafe | Compilação, execução dos testes de unidade e dos testes de API |

## Swagger / OpenAPI

Com a aplicação em execução, acesse a interface interativa em:

**http://localhost:8080/swagger-ui/index.html**

A especificação OpenAPI em JSON também está disponível em **http://localhost:8080/v3/api-docs**. No Swagger UI, é possível consultar os contratos e exemplos e executar requisições diretamente.

## Requisitos

- JDK 25 ou superior instalado e configurado no `PATH`.
- MongoDB em execução localmente na porta `27017`.
- Git para clonar o projeto.

O Maven Wrapper (`mvnw.cmd`) está incluído; não é necessário instalar Maven separadamente.

## Configuração e execução

1. **Clone o repositório e entre na pasta do projeto:**

   ```powershell
   git clone https://github.com/CrisJacques/workshop-spring-mongodb.git
   cd workshop-spring-mongodb
   ```

2. **Instale e inicie o MongoDB.** Você pode instalar o MongoDB Community Server e deixá-lo ativo como serviço local. Como alternativa, se tiver Docker instalado, inicie uma instância local para desenvolvimento:

   ```powershell
   docker run --name workshop-mongodb -p 27017:27017 -d mongo:8.0
   ```

   A aplicação usa por padrão a URI `mongodb://localhost:27017/workshop_mongo`. O MongoDB cria o banco `workshop_mongo` quando a aplicação grava os primeiros dados; não é necessário criá-lo manualmente. Se usar outra URI, configure a propriedade `spring.mongodb.uri` em `src/main/resources/application.properties` ou defina a variável de ambiente `SPRING_MONGODB_URI`.

3. **Compile, execute os testes e verifique a qualidade:**

   ```powershell
   .\mvnw.cmd clean verify
   ```

   Esse comando executa os testes de unidade, os testes de API, a verificação de cobertura JaCoCo e o Checkstyle.

4. **Inicie a aplicação:**

   ```powershell
   .\mvnw.cmd spring-boot:run
   ```

   A API ficará disponível em `http://localhost:8080`.

## Endpoints

| Método | Endpoint | Descrição |
|---|---|---|
| `GET` | `/users` | Lista os usuários |
| `GET` | `/users/{id}` | Busca um usuário por ID |
| `POST` | `/users` | Cadastra um usuário |
| `PUT` | `/users/{id}` | Atualiza nome e e-mail |
| `DELETE` | `/users/{id}` | Exclui um usuário |
| `GET` | `/users/{id}/posts` | Lista as publicações do usuário |
| `GET` | `/posts/{id}` | Busca uma publicação por ID |
| `GET` | `/posts/titlesearch?text={texto}` | Pesquisa no título |
| `GET` | `/posts/fullsearch?text={texto}&minDate={yyyy-MM-dd}&maxDate={yyyy-MM-dd}` | Pesquisa por texto e intervalo de datas |

Na busca completa, `text`, `minDate` e `maxDate` são opcionais. As datas devem usar o formato `yyyy-MM-dd`; sem datas informadas, o intervalo vai de `1970-01-01` até a data atual.

## Qualidade e testes

O projeto possui testes de unidade para os principais componentes e testes de API com RestAssured. O relatório JaCoCo gerado para a suíte unitária registra **mais de 90% de cobertura de linhas** (97% no relatório disponível). O build verifica um limite mínimo configurado de 80% de cobertura de linhas. O Checkstyle aplica as regras Google Checks e interrompe a verificação do build em caso de violações.

Para gerar novamente o relatório, execute `.\mvnw.cmd clean verify`. Depois, abra `target/site/jacoco/index.html` no navegador. Os testes de API são executados pelo Maven Failsafe durante a fase `verify`.
