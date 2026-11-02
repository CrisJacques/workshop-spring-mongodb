package com.example.workshopspringmongodb.resources;

import com.example.workshopspringmongodb.domain.Post;
import com.example.workshopspringmongodb.resources.docs.SwaggerExamples;
import com.example.workshopspringmongodb.resources.util.URL;
import com.example.workshopspringmongodb.services.PostService;
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

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping(value="/posts")
@Tag(name = "Publicações", description = "Consulta de publicações por identificador, título e período")
public class PostResource {
    @Autowired
    private PostService postService;

    @GetMapping(value = "/{id}")
    @Operation(summary = "Buscar publicação por ID", description = "Retorna uma publicação específica.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Publicação encontrada",
                    content = @Content(schema = @Schema(implementation = Post.class),
                            examples = @ExampleObject(value = SwaggerExamples.POST_RESPONSE))),
            @ApiResponse(responseCode = "404", description = "Publicação não encontrada",
                    content = @Content(schema = @Schema(
                            implementation = com.example.workshopspringmongodb.resources.exception
                                    .StandardError.class),
                            examples = @ExampleObject(value = SwaggerExamples.ERROR_RESPONSE)))
    })
    public ResponseEntity<Post> findById(
            @Parameter(description = "Identificador da publicação", example = "507f1f77bcf86cd799439022")
            @PathVariable String id) {
        return ResponseEntity.ok().body(postService.findById(id));
    }

    /**
     * @RequestParam é usado para query params da URL. O value diz para o Spring qual é o query param que deve ser lido
     * e o defaultValue diz qual valor deve ser assumido caso o query param não seja informado na request.
     */
    @GetMapping(value = "/titlesearch")
    @Operation(summary = "Buscar publicações por título",
            description = "Pesquisa publicações pelo texto contido no título.")
    @ApiResponse(responseCode = "200", description = "Resultados da busca",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Post.class)),
                    examples = @ExampleObject(value = SwaggerExamples.POSTS_RESPONSE)))
    public ResponseEntity<List<Post>> findByTitle(
            @Parameter(description = "Texto a pesquisar no título", example = "Spring")
            @RequestParam(value="text", defaultValue = "") String text) {
        return ResponseEntity.ok().body(postService.findByTitle(URL.decodeParam(text)));
    }

    @GetMapping(value = "/fullsearch")
    @Operation(summary = "Buscar publicações por texto e período",
            description = "Filtra publicações por texto no título e intervalo de datas inclusivo.")
    @ApiResponse(responseCode = "200", description = "Resultados da busca",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Post.class)),
                    examples = @ExampleObject(value = SwaggerExamples.POSTS_RESPONSE)))
    public ResponseEntity<List<Post>> fullSearch(
            @Parameter(description = "Texto a pesquisar no título", example = "Spring")
            @RequestParam(value="text", defaultValue = "") String text,
            @Parameter(description = "Data inicial no formato yyyy-MM-dd", example = "2026-01-01")
            @RequestParam(value="minDate", defaultValue = "") String minDate,
            @Parameter(description = "Data final no formato yyyy-MM-dd", example = "2026-12-31")
            @RequestParam(value="maxDate", defaultValue = "") String maxDate) {
        return ResponseEntity.ok().body(postService.fullSearch(
                URL.decodeParam(text),
                URL.convertDate(minDate, LocalDate.parse("01/01/1970", DateTimeFormatter.ofPattern("dd/MM/yyyy"))),
                URL.convertDate(maxDate, LocalDate.now()
                )));
    }

}
