package nl.novi.backendeindopdracht.controllers;
import jakarta.validation.Valid;
import nl.novi.backendeindopdracht.dto.BookInputDto;
import nl.novi.backendeindopdracht.dto.BookOutputDto;
import nl.novi.backendeindopdracht.service.BookService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookOutputDto> createBook(@Valid @RequestBody BookInputDto bookInputDto) {
        BookOutputDto createdBook = bookService.createBook(bookInputDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdBook.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
                .body(createdBook);
    }

    @GetMapping
    public ResponseEntity<List<BookOutputDto>> getBooks(
            @RequestParam(required = false) Long genreId,
            @RequestParam(required = false) String genreName,
            @RequestParam(required = false) String title) {

        if (genreId != null) {
            List<BookOutputDto> books = bookService.getBooksByGenreId(genreId);
            return ResponseEntity.ok(books);
        }

        if (genreName != null && !genreName.isBlank()) {
            List<BookOutputDto> books = bookService.getBooksByGenreName(genreName);
            return ResponseEntity.ok(books);
        }


        if (title != null && !title.isBlank()) {
            List<BookOutputDto> books = bookService.getBooksByTitle(title);
            return ResponseEntity.ok(books);
        }

        List<BookOutputDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookOutputDto> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/file")
    public ResponseEntity<String> uploadBookFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        String filename = bookService.uploadBookFile(id, file);
        return ResponseEntity.ok("File uploaded successfully: " + filename);
    }

    @GetMapping("/{id}/file")
    public ResponseEntity<Resource> getBookFile(@PathVariable Long id) {
        FileResponseDto fileResponse = bookService.getBookFileAsResource(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileResponse.getFilename() + "\"")
                .contentType(MediaType.parseMediaType(fileResponse.getContentType()))
                .body(fileResponse.getResource());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookOutputDto> updateBook(@PathVariable Long id, @Valid @RequestBody BookInputDto bookInputDto) {
        BookOutputDto updatedBook = bookService.updateBook(id, bookInputDto);
        return ResponseEntity.ok(updatedBook);
    }
}
