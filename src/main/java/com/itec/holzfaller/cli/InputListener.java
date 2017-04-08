package com.itec.holzfaller.cli;

import java.util.Scanner;

import static com.itec.holzfaller.cli.Commands.*;

public class InputListener {

    private CommandExecutor executor = new CommandExecutor();

    public void awaitCommands() {
        boolean terminate = false;

        Scanner input = new Scanner(System.in);

        while (!terminate) {
            System.out.print("be-travel>");
            String command = input.nextLine();

            executor.execute(command);

            terminate = EXIT.equals(command);
        }
    }

}
