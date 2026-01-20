package nl.novi.backendeindopdracht.mapper;


import nl.novi.backendeindopdracht.dto.GenreInputDto;
import nl.novi.backendeindopdracht.dto.GenreOutputDto;
import nl.novi.backendeindopdracht.models.Genre;

public class GenreMapper {


private GenreMapper() {

}




public static Genre toEntity(GenreInputDto genreInputDto) {

    Genre genre = new Genre();
    genre.setName(genreInputDto.name);

    return genre;
}

public static GenreOutputDto toDto(Genre genre) {

    GenreOutputDto genreOutputDto = new GenreOutputDto();

    genreOutputDto.id = genre.getId();
    genreOutputDto.name = genre.getName();

    return genreOutputDto;

}

}
