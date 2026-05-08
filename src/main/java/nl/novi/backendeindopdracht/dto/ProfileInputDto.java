package nl.novi.backendeindopdracht.dto;
import jakarta.validation.constraints.NotBlank;



public class ProfileInputDto {
   @NotBlank(message = "First name is required")
    public String firstName;
   @NotBlank (message = "Last name is required")
    public String lastName;
   @NotBlank(message = "Address is required")
    public String address;

}
