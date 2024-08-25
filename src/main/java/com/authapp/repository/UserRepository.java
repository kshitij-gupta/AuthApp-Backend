package com.authapp.repository;
import com.authapp.model.User;
public interface UserRepository {
    int save(User user);
    User retrieve(User user);
}