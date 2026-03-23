package nl.novi.backendeindopdracht.service;


import nl.novi.backendeindopdracht.dto.BookInputDto;
import nl.novi.backendeindopdracht.dto.BookOutputDto;
import nl.novi.backendeindopdracht.exception.ResourceNotFoundException;
import nl.novi.backendeindopdracht.mapper.BookMapper;
import nl.novi.backendeindopdracht.models.Book;
import nl.novi.backendeindopdracht.models.Genre;
import nl.novi.backendeindopdracht.repository.BookRepository;
import nl.novi.backendeindopdracht.repository.GenreRepository;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Service
public class BookService {


private final BookRepository bookRepository;
private final GenreRepository genreRepository;
private final GenreService genreService;

   private final Path uploadDir = Paths.get("uploads/books");

public BookService(BookRepository bookRepository, GenreRepository genreRepository, GenreService genreService) throws IOException {

    this.bookRepository = bookRepository;
    this.genreRepository = genreRepository;
    this.genreService = genreService;

    if(!Files.exists(uploadDir)) {
        Files.createDirectories(uploadDir);

        }

    }


public BookOutputDto createBook(BookInputDto bookInputDto) {
    Genre genre = genreRepository.findById(bookInputDto.genreId)
            .orElseThrow(() -> new ResourceNotFoundException("Genre not found "));


    Book book = BookMapper.toEntity(bookInputDto);
    book.setGenre(genre);



    Book savedBook = bookRepository.save(book);
    return BookMapper.toBookOutputDto(savedBook);


}


    public List<BookOutputDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();


                if(books.isEmpty()){
                    throw new ResourceNotFoundException("No books found in the database");
                }

                return books.stream()
                .map(BookMapper::toBookOutputDto)
                .toList();
    }


public BookOutputDto getBook(Long id) {
    Book book = bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found "));
    return BookMapper.toBookOutputDto(book);

}

public void deleteBook(Long id) {

    if(!bookRepository.existsById(id)) {
        throw new ResourceNotFoundException("Book is not deleted ");
    }

    bookRepository.deleteById(id);
}



public Book getBookEntity(Long id){

    return bookRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Book not found " + id));


}

    public void uploadBookFile(Long id, MultipartFile file) throws IOException {
        Book book = getBookEntity(id);

        if (!file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        String filename = file.getOriginalFilename();
        Path filePath = uploadDir.resolve(filename);
        file.transferTo(filePath);


        book.setImagePath(filename);
        bookRepository.save(book);
    }


    public Path getBookImagePath(Long id) {
        Book book = getBookEntity(id);

        if (book.getImagePath() == null) {
            throw new RuntimeException("Book has no image");
        }

        Path filePath = uploadDir.resolve(book.getImagePath());
        if (!Files.exists(filePath)) {
            throw new RuntimeException("Image file not found");
        }

        return filePath;
    }



    public BookOutputDto updateBook(Long id, BookInputDto bookInputDto) {


    Book book = bookRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Book not found " + id));



        if(bookInputDto.title!= null){
        book.setTitle(bookInputDto.title);

        }

        if (bookInputDto.author!= null) {
            book.setAuthor(bookInputDto.author);
        }

        if (bookInputDto.isbn!= null) {
            book.setIsbn(bookInputDto.isbn);

        }
        if (bookInputDto.totalCopies!= 0) {
            book.setTotalCopies(bookInputDto.totalCopies);
        }


        if (bookInputDto.genreId!= 0) {
            Genre genre = genreService.getGenreEntityById(bookInputDto.genreId);

            book.setGenre(genre);
        }

            Book savedBook = bookRepository.save(book);



BookOutputDto bookOutputDto = new BookOutputDto();
bookOutputDto.setId(savedBook.getId());
bookOutputDto.setTitle(savedBook.getTitle());
bookOutputDto.setAuthor(savedBook.getAuthor());
bookOutputDto.setIsbn(savedBook.getIsbn());
bookOutputDto.setTotalCopies(savedBook.getTotalCopies());
bookOutputDto.setAvailableCopies(savedBook.getAvailableCopies());

if(savedBook.getGenre() != null){
    bookOutputDto.setGenreName(savedBook.getGenre().getName());
}


return bookOutputDto;

    }

}
