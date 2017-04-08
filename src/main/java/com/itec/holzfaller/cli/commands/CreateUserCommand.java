package com.itec.holzfaller.cli.commands;

import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.Role;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.services.UserService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CreateUserCommand implements Command {

    private Options options = new Options();
    private UserService userService = new UserService();

    public CreateUserCommand() {
        Option usernameOption = new Option("u", "username", true, "Username");
        Option passwordOption = new Option("p", "password", true, "Password");
        Option emailOption = new Option("e", "email", true, "Email");

        options.addOption(usernameOption);
        options.addOption(passwordOption);
        options.addOption(emailOption);
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public void execute(CommandLine cmd) {
        if (LoggedUserService.loggedUser != null) {
            String username = cmd.getOptionValue("username");
            String password = cmd.getOptionValue("password");
            String email = cmd.getOptionValue("email");

            if (LoggedUserService.isConsultant()) {
                System.out.println("you must be admin to create users");
            } else {
                User user = new User(username, password, email, Role.CONSULTANT);
                userService.update(user);
            }

        } else {
            System.out.println("You must login to performs this command");
        }
    }
}
