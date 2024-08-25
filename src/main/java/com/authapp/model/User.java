package com.authapp.model;
import java.util.Objects;
 
public class User {
 
    private Long id;
    private String name;
    private String email;
    private String password;
 
    User() {}
 
    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
 
    public Long getId() {
        return this.id;
    }
    public String getName() {
        return this.name;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
 
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPassword(String password) {
        this.password = password;
    }
 
    @Override
    public boolean equals(Object o) {
 
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User employee = (User) o;
        return Objects.equals(this.id, employee.id);
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }
 
    @Override
    public String toString() {
        return "Employee{'name='" + this.name + '\'' + ", email='" + this.email + '\'' + '}';
    }
}