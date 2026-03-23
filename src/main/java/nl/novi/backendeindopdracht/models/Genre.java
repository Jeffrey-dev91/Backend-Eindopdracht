package nl.novi.backendeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "genres")

public class Genre {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
private Long id;


@Column(unique = true)
private String name;


@OneToMany(mappedBy = "genre")

@JsonIgnore
private List<Book> books = new ArrayList<>();


public Genre() {}


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}
