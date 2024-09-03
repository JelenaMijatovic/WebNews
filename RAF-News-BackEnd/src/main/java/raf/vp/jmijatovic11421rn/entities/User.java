package raf.vp.jmijatovic11421rn.entities;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class User {

    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotNull
    private String userType;
    @NotNull
    private Boolean active;
    @NotBlank
    private String password;

    public User() {

    }

    public User(String email, String firstName, String lastName, String userType, Boolean active, String password) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userType = userType;
        this.active = active;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUserType() {
        return userType;
    }

    public Boolean isActive() {
        return active;
    }

    public String getPassword() {
        return password;
    }
}
