package nl.novi.backendeindopdracht.controllers;


import nl.novi.backendeindopdracht.dto.GenreInputDto;
import nl.novi.backendeindopdracht.dto.GenreOutputDto;


import nl.novi.backendeindopdracht.service.GenreService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/genres")
public class GenreController {


    private final GenreService genreService;


    public GenreController(GenreService genreService) {
        this.genreService = genreService;

    }


    @PostMapping("/create")
    public ResponseEntity<GenreOutputDto> createGenre(@RequestBody GenreInputDto genreInputDto) {
        GenreOutputDto createdGenre = genreService.createGenre(genreInputDto);


        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdGenre);


    }

    @GetMapping("/all")
    public ResponseEntity<List<GenreOutputDto>> getAllGenres() {

        List<GenreOutputDto> genres = genreService.getAllGenres();

        return ResponseEntity.ok(genres);

    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreOutputDto> getGenreById(@PathVariable Long id) {


        return ResponseEntity.ok(genreService.getGenreById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGenreById(@PathVariable Long id) {

        genreService.deleteGenre(id);
        return ResponseEntity.noContent().build();


    }


    @PatchMapping("/{id}")
    public ResponseEntity<GenreOutputDto> updateGenreById(@PathVariable Long id, @RequestBody GenreInputDto genreInputDto) {
        GenreOutputDto updatedGenre = genreService.updateGenre(id, genreInputDto);
        return ResponseEntity.ok(updatedGenre);

    }

}

