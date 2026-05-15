package nl.novi.backendeindopdracht.service;
import nl.novi.backendeindopdracht.controllers.FileResponseDto;
import nl.novi.backendeindopdracht.dto.BookInputDto;
import nl.novi.backendeindopdracht.dto.BookOutputDto;
import nl.novi.backendeindopdracht.exception.BadRequestException;
import nl.novi.backendeindopdracht.exception.ResourceNotFoundException;
import nl.novi.backendeindopdracht.models.Book;
import nl.novi.backendeindopdracht.models.Genre;
import nl.novi.backendeindopdracht.repository.BookRepository;
import nl.novi.backendeindopdracht.repository.GenreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private GenreService genreService;

    @InjectMocks
    private BookService bookService;

    private Book book;
    private Genre genre;

    @BeforeEach
    void setUp() {
        genre = new Genre();
        genre.setId(1L);
        genre.setName("Fantasy");

        book = new Book();
        book.setId(1L);
        book.setTitle("Harry Potter");
        book.setAuthor("Author");
        book.setIsbn("123");
        book.setTotalCopies(5);
        book.setAvailableCopies(5);
        book.setGenre(genre);
    }

    @Test
    void createBook_returnsBookOutputDto_WhenGenreExists() {

        BookInputDto inputDto = new BookInputDto();
        inputDto.title = "Harry Potter";
        inputDto.author = "Author";
        inputDto.isbn = "123";
        inputDto.totalCopies = 5;
        inputDto.genreId = 1L;

        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        BookOutputDto result = bookService.createBook(inputDto);

        assertNotNull(result);
        assertEquals("Harry Potter", result.getTitle());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void createBook_throwsResourceNotFoundException_WhenGenreDoesNotExist() {

        BookInputDto inputDto = new BookInputDto();
        inputDto.genreId = 99L;
        when(genreRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> bookService.createBook(inputDto));
    }

    @Test
    void getAllBooks_returnsListOfBookOutputDtos() {
        when(bookRepository.findAll()).thenReturn(List.of(book));

        List<BookOutputDto> result = bookService.getAllBooks();
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Harry Potter", result.getFirst().getTitle());
    }

    @Test
    void getBook_returnsBookOutputDto() {

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        BookOutputDto dto = bookService.getBook(1L);
        assertNotNull(dto);
        assertEquals("Harry Potter", dto.getTitle());
    }

    @Test
    void getBook_throwsException_WhenBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> bookService.getBook(1L));
    }

    @Test
    void deleteBook_deletesBook_WhenBookExists() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);
        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteBook_throwsResourceNotFoundException_WhenBookDoesNotExist() {
        when(bookRepository.existsById(99L)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> bookService.deleteBook(99L));
    }

    @Test
    void uploadBookFile_throwsBadRequestException_whenFileEmpty() {
        MockMultipartFile file = new MockMultipartFile("file", "", "image/jpeg", new byte[0]);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        assertThrows(BadRequestException.class, () -> bookService.uploadBookFile(1L, file));
    }

    @Test
    void uploadBookFile_savesFilename_whenFileIsValid() throws IOException {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", "image/jpeg", "dummy data".getBytes());
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        String filename = bookService.uploadBookFile(1L, file);

        assertEquals("test.jpg", filename);
        verify(bookRepository, times(1)).save(book);

        Files.deleteIfExists(Paths.get("uploads/books/test.jpg"));
    }

    @Test
    void getBookImagePath_throwsResourceNotFoundException_whenNoImagePath() {
        book.setImagePath(null);
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> bookService.getBookImagePath(1L));
        assertEquals("Book has no image", ex.getMessage());
    }

    @Test
    void getBookImagePath_throwsResourceNotFoundException_whenFileNotFound() {
        book.setImagePath("missing.jpg");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> bookService.getBookImagePath(1L));
        assertEquals("Image file not found", ex.getMessage());
    }

    @Test
    void getBookFileAsResource_returnsFileResponseDto_WhenFileExists() throws IOException {
        Path uploadDir = Paths.get("uploads/books");
        if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);
        Path testFile = uploadDir.resolve("test.jpg");
        Files.write(testFile, "dummy content".getBytes());

        book.setImagePath("test.jpg");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        FileResponseDto response = bookService.getBookFileAsResource(1L);

        assertNotNull(response);
        assertEquals("test.jpg", response.getFilename());

        Files.deleteIfExists(testFile);
    }

    @Test
    void updateBook_updatesAllFields_WhenValid() {
        BookInputDto inputDto = new BookInputDto();
        inputDto.title = "New Title";
        inputDto.author = "New Author";
        inputDto.isbn = "999";
        inputDto.totalCopies = 10;
        inputDto.genreId = 1L;

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(genreService.getGenreEntityById(1L)).thenReturn(genre);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        BookOutputDto result = bookService.updateBook(1L, inputDto);
        assertNotNull(result);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void getBooksByGenreId_returnsListOfBooks() {
        when(bookRepository.findByGenreId(1L)).thenReturn(List.of(book));
        List<BookOutputDto> result = bookService.getBooksByGenreId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getBooksByGenreName_returnsListOfBooks() {

        when(bookRepository.findByGenreName("Fantasy")).thenReturn(List.of(book));

        List<BookOutputDto> result = bookService.getBooksByGenreName("Fantasy");

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getBooksByTitle_returnsListOfBooks() {
        when(bookRepository.findByTitle("Harry Potter")).thenReturn(List.of(book));

        List<BookOutputDto> result = bookService.getBooksByTitle("Harry Potter");

        assertNotNull(result);
        assertEquals(1, result.size());
    }
}
