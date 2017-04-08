package com.itec.holzfaller.cli;

import com.itec.holzfaller.cli.commands.Command;
import com.itec.holzfaller.cli.commands.EditUserCommand;
import com.itec.holzfaller.cli.commands.ListUsers;
import com.itec.holzfaller.cli.commands.LoginCommand;

import java.util.Arrays;

public class CommandExecutor {

    public void execute(String command) {
        String[] splitCommand = command.split(" ");

        if (Commands.ALL_COMMANDS.contains(splitCommand[0])) {
            Command cmd = getCommand(splitCommand[0]);
            String[] args = Arrays.copyOfRange(splitCommand, 1, splitCommand.length);
            cmd.execute(args, splitCommand[0]);
        } else {
            System.out.println("unknown command -> " + splitCommand[0]);
        }
    }

    private Command getCommand(String command) {
        switch (command) {
            case Commands.LOGIN:
                return new LoginCommand();

            case Commands.LIST_USERS:
                return new ListUsers();

            case Commands.EDIT_USER:
                return new EditUserCommand();

            case Commands.EXIT:
                System.out.println("bye bye...");
                System.exit(0);

            default:
                throw new IllegalArgumentException("Unimplemented command");
        }
    }

}
