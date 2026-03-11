package nl.novi.backendeindopdracht.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "books")

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

//@Column(nullable = true)
private String imagePath;
private String filePath;
private String fileType;


//    @Column(nullable = false)
    private String title;

//    @Column(nullable = false)
    private String author;

//    @Column(nullable = true)
    private String isbn;

//    @NotNull(message = "Total Copies is required")
    @Column(name = "totalcopies")
    private int totalCopies;

//    @NotNull(message = "Available Copies is required")
    @Column(name = "availablecopies")
    private int availableCopies;


@ManyToOne
    @JoinColumn(name = "genre_id")

private Genre genre;


    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<Loan> loans = new ArrayList<>();


public Book(String title, String author, String isbn, int totalCopies){

    this.title=title;
    this.author = author;
    this.isbn = isbn;

    this.totalCopies = totalCopies;
    this.availableCopies = totalCopies;

}

    public Book() {

    }


    public Long getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getTotalCopies() {
        return totalCopies;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
    }

    public int getAvailableCopies() {
        return availableCopies;
    }


    public void setAvailableCopies(int availableCopies) {
        this.availableCopies = availableCopies;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }


    public void loanOut(){
        if (availableCopies <= 0){
            throw new IllegalArgumentException("no copies available");

        }

        this.availableCopies--;

    }


    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void returnCopy(){

        if(availableCopies < totalCopies){
            this.availableCopies++;
        }

    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }
}
