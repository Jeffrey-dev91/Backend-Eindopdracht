package nl.novi.backendeindopdracht.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="users")


public class User {

  @Id

  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;


  @Column(length=128, nullable=false)
  private String username;

    @Column(length=128, nullable=false)
    private String password;

    @Column(length=128, nullable=false)
    private String email;




    @ManyToMany(fetch=FetchType.EAGER)
@JoinTable(name = "users_roles",
joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "rolename")

)

    private List<Role> roles = new ArrayList<>();



    @OneToOne(cascade=CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "profile_id")
    private Profile profile;



    @OneToMany(mappedBy = "user")
    private List<Loan> loans = new ArrayList<>();



public User(){}


    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public List<Role> getRoles() {
        return roles;
    }


    public void addRole(Role role) {
        this.roles.add(role);
    }


    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Loan> getLoans() {
        return loans;
    }

}
