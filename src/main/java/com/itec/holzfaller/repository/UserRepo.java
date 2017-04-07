package com.itec.holzfaller.repository;

import com.itec.holzfaller.entities.User;

public class UserRepo extends Repository<User, Long> {

    public UserRepo() {
        super(User.class);
    }
}
