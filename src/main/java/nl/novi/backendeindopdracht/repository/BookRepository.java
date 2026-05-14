package nl.novi.backendeindopdracht.repository;


import nl.novi.backendeindopdracht.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface BookRepository  extends JpaRepository<Book, Long> {
boolean existsByGenreId(Long genreId);


    List<Book> findByGenreId(Long genreId);
    List<Book> findByGenreName(String genreName);
    List<Book> findByTitle(String title);

}
