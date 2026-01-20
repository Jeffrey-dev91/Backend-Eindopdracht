package nl.novi.backendeindopdracht.models;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "loans")



public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;


    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    private User user;


    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;


    @Column(nullable = false)
    private LocalDate loanDate;
    private LocalDate returnDate;
    private boolean returned;

    public boolean isReturned() {
        return returned;
    }
    public void setReturned(boolean returned) {
        this.returned = returned;
    }

    public long getId() {
        return id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
