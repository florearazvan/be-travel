package com.itec.holzfaller.services;

import com.itec.holzfaller.repository.UserRepo;

public class LoginService {

    private UserRepo userRepo = new UserRepo();

    public boolean loginUser(String username, String password) {
        return userRepo.findByUsernameAndPassword(username, password) != null;
    }

}
