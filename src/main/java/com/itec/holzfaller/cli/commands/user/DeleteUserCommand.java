package com.itec.holzfaller.cli.commands.user;

import com.itec.holzfaller.cli.commands.Command;
import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.Role;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.services.UserService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class DeleteUserCommand implements Command {

    private Options options = new Options();
    private UserService userService = new UserService();

    public DeleteUserCommand() {
        Option usernameOption = new Option("u", "username", true, "Username");
        options.addOption(usernameOption);
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public void execute(CommandLine cmd) {
        if (LoggedUserService.loggedUser != null) {
            String username = cmd.getOptionValue("username");

            if (LoggedUserService.isConsultant()) {
                System.out.println("you must be admin to create users");
            } else {
                User user = userService.findByUsername(username);
                if (user != null) {
                    userService.delete(user);
                } else {
                    System.out.println("this user doesn't exist");
                }
            }

        } else {
            System.out.println("You must login to performs this command");
        }
    }
}
