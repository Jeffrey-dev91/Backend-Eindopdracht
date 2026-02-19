package nl.novi.backendeindopdracht.mapper;


import nl.novi.backendeindopdracht.dto.UserInputDto;
import nl.novi.backendeindopdracht.dto.UserOutputDto;
import nl.novi.backendeindopdracht.models.Profile;
import nl.novi.backendeindopdracht.models.Role;
import nl.novi.backendeindopdracht.models.User;

import java.util.List;

public class UserMapper {


    private UserMapper(){}


    public static User toEntity(UserInputDto userInputDto) {



        User user = new User();


        user.setUsername(userInputDto.getUsername());
        user.setPassword(userInputDto.getPassword());
        user.setEmail(userInputDto.getEmail());


        Profile profile = new Profile();
        profile.setFirstName(userInputDto.getFirstName());
        profile.setLastName(userInputDto.getLastName());
        profile.setAddress(userInputDto.getAddress());

        user.setProfile(profile);
        profile.setUser(user);
        return user;

    }


    public static UserOutputDto toOutputDto(User user) {

        UserOutputDto dto = new UserOutputDto();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());


        List<String> roles = user.getRoles()
                .stream()
                .map(Role::getRolename)
                .toList();

    dto.setRoles(roles);


    if(user.getProfile() != null) {
        dto.setFirstName(user.getProfile().getFirstName());
        dto.setLastName(user.getProfile().getLastName());
        dto.setAddress(user.getProfile().getAddress());

    }

    return dto;

    }

}
