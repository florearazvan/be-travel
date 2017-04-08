package com.itec.holzfaller.cli.commands;

import org.apache.commons.cli.*;

public interface Command {

    default void execute(String[] command, String commandName) {
        HelpFormatter formatter = new HelpFormatter();

        try {
            if (command.length == 0) {
                throw new ParseException("");
            }

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = parser.parse(getOptions(), command);

            execute(cmd);
        } catch (ParseException e) {
            formatter.printHelp(commandName, getOptions());
        }
    }

    Options getOptions();
    void execute(CommandLine cmd);

}
