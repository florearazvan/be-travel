package com.itec.holzfaller.services;

import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.repository.UserRepo;

import java.util.List;

public class UserService {

    private UserRepo userRepo = new UserRepo();

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public User findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public User update(User user) {
        return userRepo.update(user);
    }
}
