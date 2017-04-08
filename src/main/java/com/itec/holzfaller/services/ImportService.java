package com.itec.holzfaller.services;

import com.google.gson.Gson;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.entities.Users;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by rbu on 4/8/17.
 */
public class ImportService {

    UserService userService = new UserService();

    public boolean importJson(File file){
        try {
        Gson gson = new Gson();
        Users users = null;

        if (file != null && file.getPath() != null) {
            users = gson.fromJson(new FileReader(file.getPath()), Users.class);
        }
        if(users != null){
            for(User user : users.getUsers()){
                userService.save(user);
            }
            return true;
        }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

}
