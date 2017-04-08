package com.itec.holzfaller.services;

import com.google.gson.Gson;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.entities.Users;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class UserExportService {

    private UserService userService = new UserService();

    public void export(String fileName) throws IOException {
        String name = fileName == null ? System.getProperty("user.dir") + "/src/main/resources/users.json" : fileName;

        List<User> users = userService.findAll();
        Gson gson = new Gson();

        String json = gson.toJson(new Users(users));
        Files.write(Paths.get(name), json.getBytes());
        System.out.println(json);
    }

}
