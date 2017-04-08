package com.itec.holzfaller.cli.commands.user;

import com.itec.holzfaller.cli.commands.Command;
import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.services.UserService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.List;

public class ListUsers implements Command {

    private Options options = new Options();
    private UserService userService = new UserService();

    public ListUsers() {
        Option allOption = new Option("a", "all", false, "All option");
        options.addOption(allOption);
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public void execute(CommandLine cmd) {
        if (LoggedUserService.loggedUser != null) {
            if (LoggedUserService.isAdmin()) {
                List<User> users = userService.findAll();
                print(users);
            } else {
                System.out.println("you must be ADMIN to view all users");
            }
        } else {
            System.out.println("You must login to performs this command");
        }
    }

    private void print(List<User> users) {
        System.out.println("Username \t Email \t Location \t Role");
        users.forEach(user -> {
            System.out.println(user.getUsername() + "\t" + user.getEmail() + "\t" + getLocation(user) + " " +
                    user.getRole());
        });
    }

    private String getLocation(User user) {
        return user.getLocation() == null ? "No where" : user.getLocation().toString();
    }
}
