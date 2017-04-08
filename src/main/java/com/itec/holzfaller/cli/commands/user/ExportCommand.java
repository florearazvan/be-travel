package com.itec.holzfaller.cli.commands.user;

import com.itec.holzfaller.cli.commands.Command;
import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.services.UserExportService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.io.IOException;

public class ExportCommand implements Command {

    private UserExportService userExportService = new UserExportService();
    private Options options = new Options();

    public ExportCommand() {
        Option usernameOption = new Option("a", "all", false, "All option");
        options.addOption(usernameOption);
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public void execute(CommandLine cmd) {
        if (LoggedUserService.loggedUser != null) {
            if (LoggedUserService.isAdmin()) {
                try {
                    userExportService.export(null);
                } catch (IOException e) {
                    System.out.println("export failed... :(");
                }
            } else {
                System.out.println("you must be admin to export users");
            }
        } else {
            System.out.println("You must login to performs this command");
        }
    }
}
