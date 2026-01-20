package nl.novi.backendeindopdracht.dto;


import java.time.LocalDate;

public class LoanOutputDto {


    public Long id;
    public String bookTitle;
    public String username;
    public LocalDate loanDate;
    public LocalDate returnDate;


public boolean returned;



}
