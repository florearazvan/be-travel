package com.itec.holzfaller.cli.commands;

import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.services.LoginService;
import org.apache.commons.cli.*;

public class LoginCommand implements Command {

    private Options options = new Options();
    private LoginService loginService = new LoginService();

    public LoginCommand() {
        Option usernameOption = new Option("u", "username", true, "Username to login");
        Option passwordOption = new Option("p", "password", true, "Password to login");

        options.addOption(usernameOption);
        options.addOption(passwordOption);
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public void execute(CommandLine cmd) {
        String username = cmd.getOptionValue("username");
        String password = cmd.getOptionValue("password");

        User user = loginService.loginUser(username, password);
        if (user != null) {
            LoggedUserService.loggedUser = user;
        } else {
            System.out.println("Invalid credentials");
        }
    }

}
