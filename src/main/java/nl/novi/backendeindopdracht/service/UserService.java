package nl.novi.backendeindopdracht.service;


import nl.novi.backendeindopdracht.dto.UserInputDto;
import nl.novi.backendeindopdracht.dto.UserOutputDto;
import nl.novi.backendeindopdracht.mapper.UserMapper;
import nl.novi.backendeindopdracht.models.Role;
import nl.novi.backendeindopdracht.models.User;
import nl.novi.backendeindopdracht.repository.RoleRepository;
import nl.novi.backendeindopdracht.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {



private final UserRepository userRepository;
private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
    this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

public UserOutputDto createUser(UserInputDto userInputDto) {


    User user = UserMapper.toEntity(userInputDto);


        if (userInputDto.getRoles() != null) {
            for (String rolename : userInputDto.getRoles()) {

                Role role = roleRepository.findById(rolename)
                        .orElseThrow(() -> new RuntimeException("Role not found" + rolename));
                user.addRole(role);

            }

        }

user.setPassword(passwordEncoder.encode(userInputDto.getPassword()));
    User savedUser = userRepository.save(user);
    return UserMapper.toOutputDto(savedUser);

    }

    public List<UserOutputDto> getAllUsers () {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toOutputDto)
                .collect(Collectors.toList());

    }

    public UserOutputDto getUser (Long id){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toOutputDto(user);
    }


    public void deleteUser (Long id){
        userRepository.deleteById(id);

    }


    public Optional<User> getUserByUsername(String username) {
    return userRepository.findByUsername(username);

    }






}