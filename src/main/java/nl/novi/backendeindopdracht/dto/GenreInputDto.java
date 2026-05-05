package nl.novi.backendeindopdracht.dto;

import jakarta.validation.constraints.NotBlank;






public class GenreInputDto {

@NotBlank(message = "Genrenaam is verplicht")
    public String name;



}
