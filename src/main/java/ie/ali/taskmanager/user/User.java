package ie.ali.taskmanager.user;

import javax.persistence.*;

@Entity
@Table(name = "user_entity", indexes = @Index(name = "username_index", columnList = "username", unique = true))
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    Long id;

    String username;
    String password;
    String role;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
