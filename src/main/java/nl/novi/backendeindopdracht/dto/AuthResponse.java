package nl.novi.backendeindopdracht.dto;






public class AuthResponse {

    public final String accessToken;
    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}