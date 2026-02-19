package nl.novi.backendeindopdracht.service;


import nl.novi.backendeindopdracht.dto.BookOutputDto;
import nl.novi.backendeindopdracht.exception.ResourceNotFoundException;
import nl.novi.backendeindopdracht.models.Book;
import nl.novi.backendeindopdracht.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {


    @Mock
    BookRepository bookRepository;



    @InjectMocks
    BookService bookService;



    Book book;



    @BeforeEach
    void setUp() {
        book = new Book();
        book.setTitle("Title");
        book.setAuthor("Author");
        book.setIsbn("123");
        book.setTotalCopies(5);
        book.setAvailableCopies(5);
    }




    @Test
    void getBook_returnsBookOutputDto(){

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));


        BookOutputDto dto = bookService.getBook(1L);

        assertEquals("Test Book", dto.getTitle());
        assertEquals("Test Author", dto.getAuthor());

    }



@Test
    void getBook_throwsException_WhenBookNotFound() {

        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookService.getBook(1L));

}



//@Test
//    void uploadBookFile_savesFilePath() throws IOException {
//
//
//
//        MockMultipartFile file =
//                new MockMultipartFile("file", "test.jpg",
//                        "image/jpg", "img".getBytes());
//
//
//        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
//
//        bookService.uploadBookFile(1L,file);
//
//
//        verify(bookRepository).save(book);
//        assertNotNull(book.getFilePath());
//
//
//}


@Test
    void uploadBookFile_throwsException_whenFileEmpty() throws IOException {


        MockMultipartFile file =
                new MockMultipartFile("file", "","image/jpeg", new byte[0]);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));


        assertThrows(RuntimeException.class,
                () -> bookService.uploadBookFile(1L,file));

}


    @Test
    void getBookImagePath_throwsException_whenNoImagePath() {
        Book book = new Book();
//        book.setId(1L);
        book.setImagePath(null);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> bookService.getBookImagePath(1L));

        assertEquals("Book has no image", ex.getMessage());
    }

    @Test
    void getBookImagePath_throwsException_whenFileNotFound() {
        Book book = new Book();
//        book.setId(1L);
        book.setImagePath("missing.jpg");

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> bookService.getBookImagePath(1L));

        assertEquals("Image file not found", ex.getMessage());
    }


    }



















