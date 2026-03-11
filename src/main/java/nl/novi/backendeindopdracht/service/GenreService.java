package nl.novi.backendeindopdracht.service;


import nl.novi.backendeindopdracht.dto.GenreInputDto;
import nl.novi.backendeindopdracht.dto.GenreOutputDto;
import nl.novi.backendeindopdracht.exception.BadRequestException;
import nl.novi.backendeindopdracht.exception.ResourceNotFoundException;
import nl.novi.backendeindopdracht.mapper.GenreMapper;
import nl.novi.backendeindopdracht.models.Genre;
import nl.novi.backendeindopdracht.repository.BookRepository;
import nl.novi.backendeindopdracht.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {



    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    public GenreService(GenreRepository genreRepository, BookRepository bookRepository) {

        this.genreRepository = genreRepository;
        this.bookRepository = bookRepository;
    }

    public GenreOutputDto createGenre(GenreInputDto genreInputDto) {

        if(genreRepository.findByName(genreInputDto.name).isPresent()) {

            throw new RuntimeException("Genre already exists");

        }


        Genre genre = GenreMapper.toEntity(genreInputDto);

        Genre savedGenre = genreRepository.save(genre);

        return GenreMapper.toDto(savedGenre);

    }


    public List<GenreOutputDto> getAllGenres() {

        return genreRepository.findAll()
                .stream()
                .map(GenreMapper::toDto)
                .toList();


    }

    public GenreOutputDto getGenreById(Long id) {

        Genre genre = genreRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Genre not found"));

        return GenreMapper.toDto(genre);

    }



    public GenreOutputDto updateGenre(Long id, GenreInputDto genreInputDto) {


        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Genre not found"));


        if (genreInputDto.name!= null) {
            genre.setName(genreInputDto.name);
        }

        Genre savedGenre = genreRepository.save(genre);
        return GenreMapper.toDto(savedGenre);


    }


    public void deleteGenre(Long id) {


Genre genre = genreRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Genre not found"));



     if(bookRepository.existsByGenreId(id)) {
         throw new BadRequestException("Kan genre niet verwijderen: er zijn nog boeken aan gekoppeld!!");


     }

      genreRepository.delete(genre);
    }











    public Genre getGenreEntityById(Long id) {


        return genreRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Genre niet gevonden bij Id"));

    }





}
