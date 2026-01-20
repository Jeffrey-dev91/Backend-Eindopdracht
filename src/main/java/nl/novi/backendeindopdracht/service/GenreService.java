package nl.novi.backendeindopdracht.service;


import nl.novi.backendeindopdracht.dto.GenreInputDto;
import nl.novi.backendeindopdracht.dto.GenreOutputDto;
import nl.novi.backendeindopdracht.mapper.GenreMapper;
import nl.novi.backendeindopdracht.models.Genre;
import nl.novi.backendeindopdracht.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {



    private final GenreRepository genreRepository;


    public GenreService(GenreRepository genreRepository) {

        this.genreRepository = genreRepository;

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

        genre.setName(genreInputDto.name);

        Genre updatedGenre = genreRepository.save(genre);
        return GenreMapper.toDto(updatedGenre);


    }


    public void deleteGenre(Long id) {

      if(genreRepository.existsById(id)) {
          throw new RuntimeException("Genre not found ");
      }


      genreRepository.deleteById(id);

    }

}
