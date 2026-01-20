package nl.novi.backendeindopdracht.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name="roles")


public class Role {

    @Id

@Column(nullable = false, length = 128)
    private String rolename;


@ManyToMany(mappedBy = "roles")
    private List<User> users = new ArrayList<>();


    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
