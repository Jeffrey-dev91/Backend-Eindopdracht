package nl.novi.backendeindopdracht.models;


import jakarta.persistence.*;

@Entity
public class Profile {



    @Id
    @GeneratedValue
    private long id;

    private String firstName;
    private String lastName;
    private String address;


    @OneToOne
    @JoinColumn(name = "username")
    private User user;

}
