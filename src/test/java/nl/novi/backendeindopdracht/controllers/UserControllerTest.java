package nl.novi.backendeindopdracht.controllers;

import nl.novi.backendeindopdracht.dto.UserInputDto;
import nl.novi.backendeindopdracht.dto.UserOutputDto;
import nl.novi.backendeindopdracht.repository.BookRepository;
import nl.novi.backendeindopdracht.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)

@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb_user", // Unieke naam voor deze test
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    BookRepository bookRepository;

    @MockitoBean
    UserRepository userRepository;

    @MockitoBean
    nl.novi.backendeindopdracht.service.UserService userService;


    @Test
    void createUser_validInput_returns201() throws Exception {

        UserInputDto dto = new UserInputDto();
        dto.setUsername("jeffrey");
        dto.setEmail("jeff@test.com");
        dto.setPassword("1234");


        UserOutputDto outputDto = new UserOutputDto();
        outputDto.setId(1L);
        outputDto.setUsername("jeffrey");
        outputDto.setEmail("jeff@test.com");

        when(userService.createUser(any(UserInputDto.class))).thenReturn(outputDto);


        String json = objectMapper.writeValueAsString(dto);

        mockMvc.perform(post("/users/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                .with(csrf()))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("jeffrey"));
    }



}