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
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;


@RestController
@RequestMapping("/books")
public class BookController {

private final BookService bookService;


    public BookController(BookService bookService) {

        this.bookService = bookService;

    }


    @PostMapping("/create")

    public ResponseEntity<BookOutputDto> createBook(@Valid @RequestBody BookInputDto bookInputDto) {

        BookOutputDto createdBook = bookService.createBook(bookInputDto);

        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("{id}")
                .buildAndExpand(createdBook.getId())
                .toUri();

        return ResponseEntity
                .created(uri)
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




@PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadBookFile(@PathVariable Long id, @RequestParam("file") MultipartFile file) throws IOException {


        bookService.uploadBookFile(id,file);
return ResponseEntity.ok("file uploaden successfully" + file.getOriginalFilename());


}

@GetMapping("/{id}/file")

public ResponseEntity<Resource> getBookFile(@PathVariable Long id) throws IOException {
    Path filePath = bookService.getBookImagePath(id);
    Resource resource = new org.springframework.core.io.UrlResource(filePath.toUri());


    String contentType = Files.probeContentType(filePath);
    if (contentType == null) contentType = "application/octet-stream";

    return ResponseEntity.ok()
            .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
            .contentType(MediaType.parseMediaType(contentType))
            .body(resource);
}


}













