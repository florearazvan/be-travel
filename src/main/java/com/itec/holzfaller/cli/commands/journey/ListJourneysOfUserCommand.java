package com.itec.holzfaller.cli.commands.journey;

import com.itec.holzfaller.cli.commands.Command;
import com.itec.holzfaller.common.DateUtils;
import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.Journey;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.services.UserService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.List;

public class ListJourneysOfUserCommand implements Command {

    private Options options = new Options();
    private UserService userService = new UserService();

    public ListJourneysOfUserCommand() {
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

            User user = userService.findByUsername(username);
            if (user == null) {
                System.out.println("user does not exist");
            } else {
                if (LoggedUserService.isConsultant() && LoggedUserService.loggedUser.getUsername().equals(user.getUsername())) {
                    System.out.println("cannot see other user's journeys");
                } else {
                    System.out.println("Id \t Start Date \t End Date \t Location \t Cost");

                    List<Journey> journeys = user.getJourneys();
                    if (journeys != null) {
                        journeys.forEach(this::printJourney);
                    }
                }
            }

        } else {
            System.out.println("You must login to performs this command");
        }
    }

    private void printJourney(Journey journey) {
        System.out.println(journey.getId() + "\t" + DateUtils.dateToString(journey.getStartDate()) + "\t" +
                DateUtils.dateToString(journey.getEndDate()) + "\t" + journey.getLocation() + "\t" + journey.getCost());
    }
}
