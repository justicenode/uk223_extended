package ch.noseryoung.uk.domain_models.user.dto;

import ch.noseryoung.uk.domain_models.role.Role;
import java.util.Set;

// This an example class on how a DTO could be built
// A DTO is pretty much just a regular POJO, there is no need for any fancy annotations
// This DTO is a special example as it shows that you don't need to map every single attribute
public class UserDTO {

    // Representative attributes, make sure they are called the same way as the attribute that they represent
    private int id;

    private String username;

    private String password;

    private Set<Role> roles;

    private Boolean locked;

    private Boolean enabled;

    // Standard empty constructor
    public UserDTO() {}

    // Standard getters and setters
    public int getId() {
        return id;
    }

    public UserDTO setId(int id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserDTO setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public UserDTO setPassword(String password) {
        this.password = password;
        return this;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public UserDTO setRoles(Set<Role> roles) {
        this.roles = roles;
        return this;
    }

    public Boolean getLocked() {
        return locked;
    }

    public UserDTO setLocked(Boolean locked) {
        this.locked = locked;
        return this;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public UserDTO setEnabled(Boolean enabled) {
        this.enabled = enabled;
        return this;
    }

}
