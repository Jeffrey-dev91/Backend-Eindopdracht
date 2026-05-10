package nl.novi.backendeindopdracht.service;

import nl.novi.backendeindopdracht.controllers.FileResponseDto;
import nl.novi.backendeindopdracht.dto.BookInputDto;
import nl.novi.backendeindopdracht.dto.BookOutputDto;
import nl.novi.backendeindopdracht.exception.BadRequestException;
import nl.novi.backendeindopdracht.exception.FileStorageException;
import nl.novi.backendeindopdracht.exception.ResourceNotFoundException;
import nl.novi.backendeindopdracht.mapper.BookMapper;
import nl.novi.backendeindopdracht.models.Book;
import nl.novi.backendeindopdracht.models.Genre;
import nl.novi.backendeindopdracht.repository.BookRepository;
import nl.novi.backendeindopdracht.repository.GenreRepository;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
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

        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }
    }

    public BookOutputDto createBook(BookInputDto bookInputDto) {
        Genre genre = genreRepository.findById(bookInputDto.genreId)
                .orElseThrow(() -> new ResourceNotFoundException("Genre not found with id: " + bookInputDto.genreId));

        Book book = BookMapper.toEntity(bookInputDto);
        book.setGenre(genre);
        Book savedBook = bookRepository.save(book);
        return BookMapper.toBookOutputDto(savedBook);
    }

    public List<BookOutputDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();

        return books.stream()
                .map(BookMapper::toBookOutputDto)
                .toList();
    }

    public BookOutputDto getBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
        return BookMapper.toBookOutputDto(book);
    }

    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ResourceNotFoundException("Book not found with id: " + id);
        }
        bookRepository.deleteById(id);
    }

    public Book getBookEntity(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }


    public String uploadBookFile(Long id, MultipartFile file) {
        Book book = getBookEntity(id);
        if (file.isEmpty()) {
            throw new BadRequestException("File is empty");
        }

        try {
            String filename = file.getOriginalFilename();
            Path filePath = uploadDir.resolve(filename);
            file.transferTo(filePath);

            book.setImagePath(filename);
            bookRepository.save(book);
            return filename;
        } catch (IOException e) {
            throw new FileStorageException("Could not store file. Please try again!", e);
        }
    }

    public FileResponseDto getBookFileAsResource(Long id) {
        Path filePath = getBookImagePath(id);


        try {
            Resource resource = new UrlResource(filePath.toUri());
            String contentType = Files.probeContentType(filePath);
            if (contentType == null) {
                contentType = "application/octet-stream";
            }

            return new FileResponseDto(resource, contentType, resource.getFilename());
        } catch (IOException e) {
            throw new FileStorageException("Could not read the file!", e);
        }
    }


    public Path getBookImagePath(Long id) {
        Book book = getBookEntity(id);

        if (book.getImagePath() == null) {
            throw new ResourceNotFoundException("Book has no image");
        }

        Path filePath = uploadDir.resolve(book.getImagePath());
        if (!Files.exists(filePath)) {
            throw new ResourceNotFoundException("Image file not found");
        }

        return filePath;
    }

    public BookOutputDto updateBook(Long id, BookInputDto bookInputDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));

        if (bookInputDto.title != null) {
            book.setTitle(bookInputDto.title);
        }
        if (bookInputDto.author != null) {
            book.setAuthor(bookInputDto.author);
        }
        if (bookInputDto.isbn != null) {
            book.setIsbn(bookInputDto.isbn);
        }
        if (bookInputDto.totalCopies != null) {
            book.setTotalCopies(bookInputDto.totalCopies);
        }
        if (bookInputDto.genreId != 0) {
            Genre genre = genreService.getGenreEntityById(bookInputDto.genreId);
            book.setGenre(genre);
        }

        Book savedBook = bookRepository.save(book);

        return BookMapper.toBookOutputDto(savedBook);
    }
}
