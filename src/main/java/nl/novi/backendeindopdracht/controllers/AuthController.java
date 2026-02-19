package nl.novi.backendeindopdracht.controllers;


import nl.novi.backendeindopdracht.dto.LoginRequest;
import nl.novi.backendeindopdracht.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/auth")

public class AuthController {



    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;


    public AuthController(final AuthenticationManager authenticationManager, final JwtUtil jwtUtil) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;

    }



    @PostMapping("/login")
    public String login (@RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.username(),
                        loginRequest.password()
                )
        );


        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return jwtUtil.generateToken(authentication.getName(), roles);


    }


}
