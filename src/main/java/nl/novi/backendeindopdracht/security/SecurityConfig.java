package nl.novi.backendeindopdracht.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;



@Configuration
public class SecurityConfig {


    public final JwtUtil jwtUtil;

    public SecurityConfig(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;

    }



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtUtil jwtUtil, UserDetailsService userDetailsService) throws Exception {

http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login").permitAll()

                .requestMatchers(HttpMethod.GET, "/books/**")
                .hasAnyRole("ADMIN","EMPLOYEE","USER")

                .requestMatchers(HttpMethod.POST, "/books/**")
                .hasAnyRole("ADMIN","EMPLOYEE")

                .requestMatchers(HttpMethod.PUT, "/books/**")
                .hasAnyRole("ADMIN","EMPLOYEE")

                .requestMatchers(HttpMethod.DELETE, "/books/**")
                .hasAnyRole("ADMIN")

                        .requestMatchers("/genres/**")
                        .hasAnyRole("ADMIN","EMPLOYEE")

                .requestMatchers("/loans/**").hasAnyRole("ADMIN","EMPLOYEE")
                .requestMatchers("/users/**").hasAnyRole("ADMIN")
                .anyRequest().authenticated()
        )
        .
        addFilterBefore(
                new JwtAuthenticationFilter(jwtUtil, userDetailsService),
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager( AuthenticationConfiguration configuration) throws Exception{

        {
            return configuration.getAuthenticationManager();

        }

        }
}
