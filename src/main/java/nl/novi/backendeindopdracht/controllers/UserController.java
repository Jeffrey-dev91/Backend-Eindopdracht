package nl.novi.backendeindopdracht.controllers;


import nl.novi.backendeindopdracht.repository.UserRepository;
import nl.novi.backendeindopdracht.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")

public class UserController {

    private final UserRepository userRepository;



    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }







}
