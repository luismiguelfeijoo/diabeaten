package com.diabeaten.edgeservice.model.dto;

public class Role {
    /**
     * Attributes
     */
    private Long id;
    private String role;

    /**
     * Default Constructor
     */
    public Role() {
    }

    /**
     * Constructor
     * @param role A String value
     */
    public Role(String role) {
        setRole(role);
    }

    /**Getters & Setters**/

    /**
     * This method gets Role's id
     * @return a integer value
     */
    public Long getId() {
        return id;
    }

    /**
     * This method sets Role's id
     * @param id a long value
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *  This method gets Role's role
     * @return a Role element
     */
    public String getRole() {
        return role;
    }

    /**
     * This method sets Role's role
     * @param role A String value
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * This method generate a string with a customize format.
     * @return a String value
     */
    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", authority='" + role + '\'';
    }
}
