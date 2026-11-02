package com.example.workshopspringmongodb.resources;

import com.example.workshopspringmongodb.domain.Post;
import com.example.workshopspringmongodb.domain.User;
import com.example.workshopspringmongodb.dto.UserDTO;
import com.example.workshopspringmongodb.resources.docs.SwaggerExamples;
import com.example.workshopspringmongodb.resources.exception.StandardError;
import com.example.workshopspringmongodb.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value="/users")
@Tag(name = "Usuários", description = "Cadastro e consulta de usuários e suas publicações")
public class UserResource {
    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Listar usuários", description = "Retorna todos os usuários cadastrados.")
    @ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserDTO.class)),
                    examples = @ExampleObject(value = SwaggerExamples.USERS_RESPONSE)))
    public ResponseEntity<List<UserDTO>> findAll() {
        List<User> users = userService.findAll();
        List<UserDTO> listDto = users.stream().map(UserDTO::new).toList();
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar usuário por ID", description = "Retorna os dados de um usuário.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário encontrado",
                    content = @Content(schema = @Schema(implementation = UserDTO.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_RESPONSE))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(
                            implementation = StandardError.class),
                            examples = @ExampleObject(value = SwaggerExamples.ERROR_RESPONSE)))
    })
    public ResponseEntity<UserDTO> findById(
            @Parameter(description = "Identificador do usuário", example = "507f1f77bcf86cd799439011")
            @PathVariable String id) {
        return ResponseEntity.ok().body(new UserDTO(userService.findById(id)));
    }

    @PostMapping
    @Operation(summary = "Cadastrar usuário", description = "Cria um usuário e retorna a URI do recurso.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Corpo da requisição inválido")
    })
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            required = true, description = "Dados do usuário a ser criado",
            content = @Content(schema = @Schema(implementation = UserDTO.class),
                    examples = @ExampleObject(value = SwaggerExamples.USER_REQUEST)))
    public ResponseEntity<Void> insert(@RequestBody UserDTO userDTO) {
        User user = userService.insert(userService.fromDTO(userDTO));
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).build(); // Vai retornar status 201 com payload vazio e a uri do novo recurso
        // no header
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Excluir usuário", description = "Remove um usuário existente.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário excluído"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(
                            implementation = StandardError.class),
                            examples = @ExampleObject(value = SwaggerExamples.ERROR_RESPONSE)))
    })
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Identificador do usuário", example = "507f1f77bcf86cd799439011")
            @PathVariable String id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Atualizar usuário", description = "Substitui nome e e-mail do usuário informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário atualizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(
                            implementation = StandardError.class),
                            examples = @ExampleObject(value = SwaggerExamples.ERROR_RESPONSE)))
    })
    public ResponseEntity<Void> update(
            @Parameter(description = "Identificador do usuário", example = "507f1f77bcf86cd799439011")
            @PathVariable String id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true, description = "Novos dados do usuário",
                    content = @Content(schema = @Schema(implementation = UserDTO.class),
                            examples = @ExampleObject(value = SwaggerExamples.USER_REQUEST)))
            @RequestBody UserDTO userDTO) {
        User obj = userService.fromDTO(userDTO);
        obj.setId(id);
        userService.update(obj);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/{id}/posts")
    @Operation(summary = "Listar publicações do usuário",
            description = "Retorna as publicações associadas ao usuário informado.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Publicações retornadas com sucesso",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = Post.class)),
                            examples = @ExampleObject(value = SwaggerExamples.POSTS_RESPONSE))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(
                            implementation = com.example.workshopspringmongodb.resources.exception
                                    .StandardError.class),
                            examples = @ExampleObject(value = SwaggerExamples.ERROR_RESPONSE)))
    })
    public ResponseEntity<List<Post>> findPosts(
            @Parameter(description = "Identificador do usuário", example = "507f1f77bcf86cd799439011")
            @PathVariable String id) {
        return ResponseEntity.ok().body(userService.findById(id).getPosts());
    }


}
