package nl.novi.backendeindopdracht.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.List;

public class UserInputDto {

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    public String username;

    @NotBlank(message = "Password is required")
    @Size(min = 4, message = "Password must be at least 4 characters")
    public String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    public String email;

    public List<String> roles;

    @NotBlank(message = "Firstname is required")
    public String firstName;
    @NotBlank(message = "Lastname is required")
    public String lastName;
    @NotBlank(message = "Address is required")
    public String address;

}