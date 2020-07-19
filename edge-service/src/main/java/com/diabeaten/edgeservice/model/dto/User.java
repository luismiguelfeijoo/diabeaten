package com.diabeaten.edgeservice.model.dto;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User {
    /**
     * Attributes
     */
    private Long id;
    private String username;
    private String password;
    private Set<Role> roles = new HashSet<>();

    /**
     * Default Constructor
     */
    public User() {}

    /**
     * Constructor Class
     * @param username a String value
     * @param password a String value
     */
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters & Setters

    /**
     * This method gets User's id
     * @return id (Long)
     */
    public Long getId() {
        return id;
    }

    /**
     * This method sets User's id
     * @param id a long value
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method gets User's name
     * @return name  (string)
     */
    public String getUsername() {
        return username;
    }

    /**
     * This method sets User's name
     * @param username A String value
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *  This method gets User's password
     * @return a password (string)
     */
    public String getPassword() {
        return password;
    }

    /**
     *  This method sets User's password
     * @param password a password element
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * This method gets User's roles
     * @return a Role's set
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * This method sets User's roles
     * @param roles a Rolse element
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * This method prints user with his properties
     *  @return A customize String format.
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + roles +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(roles, user.roles);
    }
}
