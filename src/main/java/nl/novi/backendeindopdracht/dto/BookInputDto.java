package nl.novi.backendeindopdracht.dto;



import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


public class BookInputDto {




//@NotBlank(message = "Title is required")
public String title;

//@NotBlank(message = "Author is required")
public String author;
//
//@NotBlank(message = "ISBN is required")
public String isbn;

//@NotNull(message = "Total Copies is required")
//@Min(value = 1, message = "Total Copies must be at least 1")
public int totalCopies;


public long genreId;

}
