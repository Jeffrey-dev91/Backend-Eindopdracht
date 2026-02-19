package nl.novi.backendeindopdracht.controllers;

import nl.novi.backendeindopdracht.models.Book;
import nl.novi.backendeindopdracht.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;

import org.springframework.http.MediaType;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.get;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootApplication

class BookControllerTest {


    @Autowired
    MockMvc mockMvc;


    @Autowired
    BookRepository bookRepository;


    @Test
    public void getBookById_existingBook_returns200() throws Exception {

        Book book = new Book();
        book.setTitle("Harry Potter");
        book.setAuthor("J.K. Rowling");
        book.setIsbn("123");

        book = bookRepository.save(book);


        mockMvc.perform((RequestBuilder) get("/books/{id}", book.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.title").value("Harry Potter"))
                .andExpect((ResultMatcher) jsonPath("$.author").value("J.K. Rowling"));
    }


}