package com.authapp.repository;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.authapp.model.User;
 
@Repository
public class JdbcUserRepository implements UserRepository {
 
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public int save(User user) {
        String encodedPassword = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return jdbcTemplate.update("INSERT INTO users (id, name, email, password) VALUES(default,?,?,?)",
                new Object[]{ user.getName(), user.getEmail(), user.getPassword()});
    }
    @Override
    public User retrieve(User user) {
        try {
            User foundUser = jdbcTemplate.queryForObject(
                "SELECT id, name, email, password FROM users WHERE email = ?",
                BeanPropertyRowMapper.newInstance(User.class),
                user.getEmail());
    
            // Compare the raw password with the stored hashed password
            if (foundUser != null && passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
                return foundUser;  // Password matches, return the user
            } else {
                return null;  // Password does not match
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;  // No user found
        }
    }
 
 
}