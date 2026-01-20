package nl.novi.backendeindopdracht.service;


import nl.novi.backendeindopdracht.dto.BookInputDto;
import nl.novi.backendeindopdracht.dto.BookOutputDto;
import nl.novi.backendeindopdracht.mapper.BookMapper;
import nl.novi.backendeindopdracht.models.Book;
import nl.novi.backendeindopdracht.models.Genre;
import nl.novi.backendeindopdracht.repository.BookRepository;
import nl.novi.backendeindopdracht.repository.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {


private final BookRepository bookRepository;
private final GenreRepository genreRepository;

public BookService(BookRepository bookRepository, GenreRepository genreRepository) {

    this.bookRepository = bookRepository;
    this.genreRepository = genreRepository;
}


public BookOutputDto createBook(BookInputDto bookInputDto) {
    Genre genre = genreRepository.findById(bookInputDto.genreId)
            .orElseThrow(() -> new RuntimeException("Genre not found"));


    Book book = BookMapper.toEntity(bookInputDto);
    book.setGenre(genre);
    return BookMapper.toBookOutputDto(bookRepository.save(book));


}


    public List<BookOutputDto> getAllBooks() {
        return bookRepository.findAll()
                .stream()
                .map(BookMapper::toBookOutputDto)
                .toList();
    }


public BookOutputDto getBook(Long id) {
    Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found"));
    return BookMapper.toBookOutputDto(book);

}

public void deleteBook(Long id) {

    if(bookRepository.existsById(id)) {
        throw new RuntimeException("Book not found");
    }

    bookRepository.deleteById(id);
}



}
