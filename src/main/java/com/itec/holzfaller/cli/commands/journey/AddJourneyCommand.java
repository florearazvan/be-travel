package com.itec.holzfaller.cli.commands.journey;

import com.itec.holzfaller.cli.commands.Command;
import com.itec.holzfaller.common.DateUtils;
import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.Journey;
import com.itec.holzfaller.entities.Location;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.repository.JourneyRepo;
import com.itec.holzfaller.repository.LocationRepo;
import com.itec.holzfaller.services.UserService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.text.ParseException;
import java.util.Date;

public class AddJourneyCommand implements Command {

    private Options options = new Options();
    private UserService userService = new UserService();
    private LocationRepo locationRepo = new LocationRepo();

    public AddJourneyCommand() {
        Option usernameOption = new Option("u", "username", true, "Username");
        Option journeyStartDateOption = new Option("start", "start", true, "Journey start date");
        Option journeyEndDateOption = new Option("end", "end", true, "Journey end date");
        Option costOption = new Option("c", "cost", true, "Journey cost");
        Option locationIdOption = new Option("loc", "location", true, "Location id");

        options.addOption(usernameOption);
        options.addOption(journeyStartDateOption);
        options.addOption(journeyEndDateOption);
        options.addOption(locationIdOption);
        options.addOption(costOption);
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public void execute(CommandLine cmd) {
        if (LoggedUserService.loggedUser != null) {
            String username = cmd.getOptionValue("username");
            String startDate = cmd.getOptionValue("start");
            String endDate = cmd.getOptionValue("end");
            String locationId = cmd.getOptionValue("location");
            String costString = cmd.getOptionValue("cost");

            try {
                long id = Long.parseLong(locationId);
                double cost = Double.parseDouble(costString);
                Date start = DateUtils.parseDate(startDate);
                Date end = DateUtils.parseDate(endDate);

                User user = userService.findByUsername(username);
                if (user == null) {
                    System.out.println("user does not exist");
                } else {
                    if (LoggedUserService.isConsultant() && LoggedUserService.loggedUser.getUsername().equals(user.getUsername())) {
                        System.out.println("cannot add a journey to another user");
                    } else {
                        Location location = locationRepo.findOne(id);

                        if (location != null) {
                            Journey journey = new Journey(start, end, cost, location);
                            user.getJourneys().add(journey);
                            userService.update(user);
                        } else {
                            System.out.println("location does not exist");
                        }
                    }
                }
            } catch (NumberFormatException e) {
                System.out.println("location-id & cost must be a numbers");
            } catch (ParseException ex) {
                System.out.println("Invalid date format. Please use dd.MM.yyyy format");
            }
        } else {
            System.out.println("You must login to performs this command");
        }
    }
}
