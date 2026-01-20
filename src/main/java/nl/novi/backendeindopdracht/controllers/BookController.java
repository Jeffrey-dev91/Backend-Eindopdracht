package nl.novi.backendeindopdracht.controllers;

import nl.novi.backendeindopdracht.dto.BookInputDto;
import nl.novi.backendeindopdracht.dto.BookOutputDto;
import nl.novi.backendeindopdracht.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

private final BookService bookService;


    public BookController(BookService bookService) {

        this.bookService = bookService;

    }


    @PostMapping("/create")

    public ResponseEntity<BookOutputDto> createBook(@RequestBody BookInputDto bookInputDto) {

        BookOutputDto createdBook = bookService.createBook(bookInputDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdBook);


    }


    @GetMapping("/all")

    public ResponseEntity <List<BookOutputDto>>getAllBooks() {

        List<BookOutputDto> books = bookService.getAllBooks();
 return ResponseEntity.ok(books);

    }


    @GetMapping("/{id}")
public ResponseEntity <BookOutputDto> getBookById(@PathVariable long id) {

        return ResponseEntity.ok(bookService.getBook(id));

    }


@DeleteMapping("/{id}")
    public ResponseEntity <Void> deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
}


}
