package nl.novi.backendeindopdracht.dto;


import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class LoanInputDto {


@NotNull(message = "Book id is verplicht")
public Long bookId;

@NotNull(message = "User id is verplicht")
public Long userId;


public LocalDate loanDate;

public LocalDate returnDate;

}
