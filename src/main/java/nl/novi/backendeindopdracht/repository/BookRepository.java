package nl.novi.backendeindopdracht.repository;


import nl.novi.backendeindopdracht.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BookRepository  extends JpaRepository<Book, Long> {
boolean existsByGenreId(Long genreId);

}
