package com.itec.holzfaller.cli.commands;

import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.services.UserService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class EditUserCommand implements Command{

    private Options options = new Options();
    private UserService userService = new UserService();

    public EditUserCommand() {
        Option usernameOption = new Option("u", "username", true, "Username to edit");
        Option passwordOption = new Option("p", "password", true, "New Password");
        Option emailOption = new Option("e", "email", true, "New Email");

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

            User currentUser = userService.findByUsername(username);
            if (LoggedUserService.isConsultant() && !LoggedUserService.loggedUser.getUsername().equals(username)) {
                System.out.println("you cannot change the credentials of another user");
            } else {
                currentUser.setPassword(password);
                currentUser.setEmail(email);
                userService.update(currentUser);
            }

        } else {
            System.out.println("You must login to performs this command");
        }

    }
}
