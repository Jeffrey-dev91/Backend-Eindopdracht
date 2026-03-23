package nl.novi.backendeindopdracht.controllers;


import nl.novi.backendeindopdracht.dto.ProfileInputDto;
import nl.novi.backendeindopdracht.dto.ProfileOutputDto;
import nl.novi.backendeindopdracht.dto.UserInputDto;
import nl.novi.backendeindopdracht.dto.UserOutputDto;
import nl.novi.backendeindopdracht.exception.BadRequestException;
import nl.novi.backendeindopdracht.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/users")

public class UserController {

    private final UserService userService;



    public UserController(UserService userService) {
        this.userService = userService;
    }



@PostMapping("/create")
    public ResponseEntity<UserOutputDto> createUser( @RequestBody(required = false) UserInputDto userInputDto) {
if(userInputDto == null) {

    throw new BadRequestException("Request body mag niet leeg zijn!");
}

        UserOutputDto createdUser = userService.createUser(userInputDto);


    URI uri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(createdUser.getId())
            .toUri();


    return ResponseEntity
            .created(uri)
            .body(createdUser);

}


@GetMapping("all")


public ResponseEntity<List<UserOutputDto>> getAllUsers() {


        List<UserOutputDto> users = userService.getAllUsers();

        return ResponseEntity.ok(users);
}



@GetMapping("/{id}")

    public ResponseEntity <UserOutputDto> getUserById(@PathVariable Long id) {


        UserOutputDto user = userService.getUser(id);
        return ResponseEntity.ok(user);



}


@DeleteMapping("/{id}")

    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();


}


@PatchMapping("/{id}")
    public ResponseEntity<UserOutputDto> updateUser(@PathVariable Long id, @RequestBody UserInputDto userInputDto) {

        UserOutputDto outputDto = userService.updateUser(id, userInputDto);
        return ResponseEntity.ok(outputDto);

}



@PutMapping("/{id}/profile")
    public ResponseEntity<ProfileOutputDto> updateUserProfile(@PathVariable Long id , @RequestBody ProfileInputDto profileInputDto) {


        ProfileOutputDto result = userService.assignProfile(id, profileInputDto);
        return ResponseEntity.ok(result);


}


@GetMapping("/{id}/profile")
    public ResponseEntity<ProfileOutputDto> getProfile(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getProfile(id));
    }



    @DeleteMapping("/{id}/profile")
    public ResponseEntity<Void> deleteProfile(@PathVariable Long id) {
        userService.deleteUserProfile(id);
        return ResponseEntity.noContent().build();


    }


}
