package com.itec.holzfaller.cli.commands.journey;

import com.itec.holzfaller.cli.commands.Command;
import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.Journey;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.repository.JourneyRepo;
import com.itec.holzfaller.services.UserService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.List;

public class DeleteJourneyCommand implements Command {

    private Options options = new Options();
    private UserService userService = new UserService();
    private JourneyRepo journeyRepo = new JourneyRepo();

    public DeleteJourneyCommand() {
        Option usernameOption = new Option("u", "username", true, "Username");
        Option journeyIdOption = new Option("j", "journey-id", true, "Journey Id");

        options.addOption(usernameOption);
        options.addOption(journeyIdOption);
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public void execute(CommandLine cmd) {
        if (LoggedUserService.loggedUser != null) {
            String username = cmd.getOptionValue("username");
            String journeyId = cmd.getOptionValue("journey-id");

            try {
                long id = Long.parseLong(journeyId);

                User user = userService.findByUsername(username);
                if (user == null) {
                    System.out.println("user does not exist");
                } else {
                    if (LoggedUserService.isConsultant() && LoggedUserService.loggedUser.getUsername().equals(user.getUsername())) {
                        System.out.println("cannot delete other user's journeys");
                    } else {
                        Journey journeyToDelete = journeyRepo.findOne(id);
                        if (journeyToDelete != null) {
                            user.getJourneys().remove(journeyToDelete);
                            userService.update(user);
                        } else {
                            System.out.println("journey does not exist");
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("journey-id must be a number");
            }
        } else {
            System.out.println("You must login to performs this command");
        }
    }
}
