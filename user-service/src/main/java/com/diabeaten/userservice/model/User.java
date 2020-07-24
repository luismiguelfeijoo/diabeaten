package com.diabeaten.userservice.model;

import com.diabeaten.userservice.util.Hashing;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {
    /**
     * Attributes
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String username;
    private String password;

    @OneToMany(fetch= FetchType.EAGER, cascade= CascadeType.ALL, mappedBy="user")
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
        setUsername(username);
        setPassword(password);
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
        this.password = Hashing.hash(password);
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
}
