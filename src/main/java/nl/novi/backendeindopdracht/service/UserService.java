package nl.novi.backendeindopdracht.service;


import nl.novi.backendeindopdracht.dto.ProfileInputDto;
import nl.novi.backendeindopdracht.dto.ProfileOutputDto;
import nl.novi.backendeindopdracht.dto.UserInputDto;
import nl.novi.backendeindopdracht.dto.UserOutputDto;
import nl.novi.backendeindopdracht.exception.ResourceNotFoundException;
import nl.novi.backendeindopdracht.mapper.UserMapper;
import nl.novi.backendeindopdracht.models.Profile;
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

    public List<UserOutputDto> getAllUsers() {

        return userRepository.findAll()
                .stream()
                .map(UserMapper::toOutputDto)
                .collect(Collectors.toList());

    }

    public UserOutputDto getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return UserMapper.toOutputDto(user);
    }


    public void deleteUser(Long id) {
        userRepository.deleteById(id);

    }


    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);

    }


    public User getUserEntityById(Long id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

    }


    public UserOutputDto updateUser(Long id, UserInputDto userInputDto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));


        if (userInputDto.username != null) {
            user.setUsername(userInputDto.username);

        }
        if (userInputDto.password != null) {
            user.setPassword(passwordEncoder.encode(userInputDto.password));
        }
        if (userInputDto.email != null) {
            user.setEmail(userInputDto.email);
        }

        Profile profile = user.getProfile();

        if (profile != null) {
            if (userInputDto.getFirstName() != null) {
                profile.setFirstName(userInputDto.firstName);
            }

            if (userInputDto.getLastName() != null) {
                profile.setLastName(userInputDto.lastName);
            }
            if (userInputDto.getAddress() != null) {
                profile.setAddress(userInputDto.address);

            }

        }

        if (userInputDto.roles != null && !userInputDto.roles.isEmpty()) {
            user.getRoles().clear();

            for (String roleName : userInputDto.roles) {
                Role role = roleRepository.findById(roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found" + roleName));

                user.addRole(role);
            }
        }
        User savedUser = userRepository.save(user);
        return UserMapper.toOutputDto(savedUser);

    }


    public ProfileOutputDto getProfile(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = user.getProfile();
        if (profile == null) {
            throw new RuntimeException("Profiel niet gevonden voor deze gebruiker");
        }


        ProfileOutputDto outputDto = new ProfileOutputDto();
        outputDto.id = profile.getId();
        outputDto.firstName = profile.getFirstName();
        outputDto.lastName = profile.getLastName();
        outputDto.address = profile.getAddress();

        return outputDto;
    }

    public ProfileOutputDto assignProfile(Long id, ProfileInputDto inputDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Profile profile = user.getProfile();
        if (profile == null) {
            profile = new Profile();
        }

        profile.setFirstName(inputDto.firstName);
        profile.setLastName(inputDto.lastName);
        profile.setAddress(inputDto.address);

        profile.setUser(user);
        user.setProfile(profile);

        userRepository.save(user);


        ProfileOutputDto outputDto = new ProfileOutputDto();
        outputDto.id = user.getProfile().getId();
        outputDto.firstName = user.getProfile().getFirstName();
        outputDto.lastName = user.getProfile().getLastName();
        outputDto.address = user.getProfile().getAddress();

        return outputDto;
    }



    public void deleteUserProfile(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));



        if(user.getProfile() != null) {
            user.setProfile(null);
            userRepository.save(user);


        }

    }









}






