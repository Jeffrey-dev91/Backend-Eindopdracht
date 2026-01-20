package nl.novi.backendeindopdracht.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="users")


public class User {

  @Id
  @Column(length=128, nullable=false)
  private String username;

    @Column(length=128, nullable=false)
  private String email;

    @Column(length=128, nullable=false)
  private String password;


    @OneToMany(mappedBy = "user")
    private List<Loan> loans = new ArrayList<>();

@OneToOne(mappedBy = "user")
private Profile profile;


    @ManyToMany(fetch=FetchType.EAGER)
@JoinTable(name = "users_roles",
joinColumns = @JoinColumn(name = "username", referencedColumnName = "username"),
        inverseJoinColumns = @JoinColumn(name = "rolename", referencedColumnName = "rolename")

)



private List<Role> roles = new ArrayList<>();

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
