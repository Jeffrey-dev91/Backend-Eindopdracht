package nl.novi.backendeindopdracht.repository;


import nl.novi.backendeindopdracht.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {

}
