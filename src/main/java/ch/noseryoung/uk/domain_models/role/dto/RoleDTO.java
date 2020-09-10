package ch.noseryoung.uk.domain_models.role.dto;

import ch.noseryoung.uk.domain_models.authority.Authority;

import java.util.Set;

// This an example class on how a DTO could be built
// A DTO is pretty much just a regular POJO, there is no need for any fancy annotations
public class RoleDTO {

    // Representative attributes, make sure they are called the same way as the attribute that they represent
    private int id;

    private String name;

    private Set<Authority> authorities;

    // Standard empty constructor
    public RoleDTO() {}

    // Standard getters and setters
    public int getId() {
        return id;
    }

    public RoleDTO setId(int id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public RoleDTO setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public RoleDTO setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
        return this;
    }
}
