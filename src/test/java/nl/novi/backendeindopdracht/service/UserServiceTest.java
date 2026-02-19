package nl.novi.backendeindopdracht.service;

import nl.novi.backendeindopdracht.dto.UserInputDto;
import nl.novi.backendeindopdracht.dto.UserOutputDto;
import nl.novi.backendeindopdracht.models.Role;
import nl.novi.backendeindopdracht.models.User;
import nl.novi.backendeindopdracht.repository.RoleRepository;
import nl.novi.backendeindopdracht.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    UserRepository userRepository;

    @Mock
    RoleRepository roleRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    UserService userService;



    @Test
    void createUser_withoutRoles_savesUser() {



        UserInputDto input = new UserInputDto();
        input.setUsername("jeffrey");
        input.setPassword("1234");



        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserOutputDto result = userService.createUser(input);

        assertEquals("jeffrey", result.getUsername());
        verify(passwordEncoder).encode("1234");

    }


    @Test
    void createUser_withRoles_assignsRolesCorrectly() {


        UserInputDto input = new UserInputDto();
        input.setUsername("admin");
        input.setPassword("secret");
        input.setRoles(List.of("ADMIN"));

        Role adminRole = new Role();
        adminRole.setRolename("ADMIN");

        when(roleRepository.findById("ADMIN"))
                .thenReturn(Optional.of(adminRole));

        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserOutputDto result = userService.createUser(input);

        assertTrue(result.getRoles().contains("ADMIN"));
        verify(passwordEncoder).encode("secret");

    }

    @Test
    void createUser_withInvalidRole_throwsException() {


        UserInputDto input = new UserInputDto();
        input.setUsername("user");
        input.setPassword("pw");
        input.setRoles(List.of("FAKE_ROLE"));

        when(roleRepository.findById("FAKE_ROLE"))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.createUser(input));

        assertTrue(ex.getMessage().contains("Role not found"));
    }

    @Test
    void getUser_existingId_returnsUser() {

        User user = new User();
        user.setUsername("ben");

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        UserOutputDto result = userService.getUser(1L);

        assertEquals("ben", result.getUsername());

    }

    @Test
    void getUser_nonExistingId_throwsException() {
        when(userRepository.findById(99L))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> userService.getUser(99L));

        assertEquals("User not found", ex.getMessage());
    }

    @Test
    void deleteUser_callsRepository() {


        userService.deleteUser(5L);

        verify(userRepository).deleteById(5L);
    }

    @Test
    void getUserByUsername_returnsOptionalUser() {


        User user = new User();
        user.setUsername("john");

        when(userRepository.findByUsername("john"))
                .thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByUsername("john");

        assertTrue(result.isPresent());
        verify(userRepository).findByUsername("john");
    }


    @Test

    void getAllUsers_returnsAllUsers() {
        User user1 = new User();
        user1.setUsername("Alice");
        User user2 = new User();
        user2.setUsername("Bob");

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserOutputDto> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(u -> u.getUsername().equals("Alice")));
        assertTrue(result.stream().anyMatch(u -> u.getUsername().equals("Bob")));
    }


}
