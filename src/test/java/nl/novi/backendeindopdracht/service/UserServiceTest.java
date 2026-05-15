package nl.novi.backendeindopdracht.service;
import nl.novi.backendeindopdracht.dto.UserInputDto;
import nl.novi.backendeindopdracht.dto.UserOutputDto;
import nl.novi.backendeindopdracht.exception.ResourceNotFoundException;
import nl.novi.backendeindopdracht.models.Role;
import nl.novi.backendeindopdracht.models.User;
import nl.novi.backendeindopdracht.repository.RoleRepository;
import nl.novi.backendeindopdracht.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        this.userService = new UserService(userRepository, roleRepository, passwordEncoder);}

    @Test
    void createUser_withoutRoles_savesUser() {
        UserInputDto input = new UserInputDto();
        input.username = "jeffrey";
        input.password = "1234";
        input.email = "jeffrey@test.nl";
        input.firstName = "Jeffrey";
        input.lastName = "Tester";
        input.address = "Teststraat 1";

        doReturn("encrypted_1234").when(passwordEncoder).encode("1234");
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserOutputDto result = userService.createUser(input);

        assertNotNull(result);
        assertEquals("jeffrey", result.getUsername());
        verify(passwordEncoder, times(1)).encode("1234");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void createUser_withRoles_assignsRolesCorrectly() {
        UserInputDto input = new UserInputDto();
        input.username = "admin";
        input.password = "secret";
        input.email = "admin@test.nl";
        input.firstName = "Admin";
        input.lastName = "Systeem";
        input.address = "Serverroom 1";
        input.roles = List.of("ADMIN");

        Role adminRole = new Role();
        adminRole.setRolename("ADMIN");

        doReturn("encrypted_secret").when(passwordEncoder).encode("secret");
        when(roleRepository.findById("ADMIN")).thenReturn(Optional.of(adminRole));
        when(userRepository.save(any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        UserOutputDto result = userService.createUser(input);

        assertNotNull(result);
        assertTrue(result.getRoles().contains("ADMIN"));
        verify(passwordEncoder, times(1)).encode("secret");
    }

    @Test
    void createUser_withInvalidRole_throwsException() {
        UserInputDto input = new UserInputDto();
        input.username = "user";
        input.password = "pw";
        input.roles = List.of("FAKE_ROLE");

        when(roleRepository.findById("FAKE_ROLE")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> userService.createUser(input));
        assertTrue(ex.getMessage().contains("Role not found"));
    }

    @Test
    void getUser_existingId_returnsUser() {
        User user = new User();
        user.setUsername("ben");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));


        UserOutputDto result = userService.getUser(1L);

        assertNotNull(result);
        assertEquals("ben", result.getUsername());
    }

    @Test
    void getUser_nonExistingId_throwsException() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> userService.getUser(99L));
        assertEquals("User not found with id: 99" , ex.getMessage());
    }


    @Test
    void deleteUser_callsRepository() {
        Long userId = 5L;
        when(userRepository.existsById(userId)).thenReturn(true);

        userService.deleteUser(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }




    @Test
    void getUserByUsername_returnsOptionalUser() {
        User user = new User();
        user.setUsername("john");
        when(userRepository.findByUsername("john")).thenReturn(Optional.of(user));

        Optional<User> result = userService.getUserByUsername("john");

        assertTrue(result.isPresent());
        assertEquals("john", result.get().getUsername());
        verify(userRepository, times(1)).findByUsername("john");
    }

    @Test
    void getAllUsers_returnsAllUsers() {
        User user1 = new User();
        user1.setUsername("Alice");
        User user2 = new User();
        user2.setUsername("Bob");
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserOutputDto> result = userService.getAllUsers();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Alice", result.getFirst().getUsername());
        assertTrue(result.stream().anyMatch(u -> u.getUsername().equals("Bob")));
    }
}
