package com.itec.holzfaller.cli.commands.user;

import com.itec.holzfaller.cli.commands.Command;
import com.itec.holzfaller.common.Constants;
import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.Location;
import com.itec.holzfaller.entities.User;
import com.itec.holzfaller.services.UserService;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class EditUserLocationCommand implements Command {

    private Options options = new Options();
    private UserService userService = new UserService();

    public EditUserLocationCommand() {
        Option usernameOption = new Option("u", "username", true, "Username to edit");
        Option latitudeOption = new Option("lt", "latitude", true, "Location latitude");
        Option longitudeOption = new Option("lg", "longitude", true, "Location longitude");
        Option nameOption = new Option("n", "name", true, "Location name");

        options.addOption(usernameOption);
        options.addOption(latitudeOption);
        options.addOption(longitudeOption);
        options.addOption(nameOption);
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public void execute(CommandLine cmd) {
        if (LoggedUserService.loggedUser != null) {
            String username = cmd.getOptionValue("username");
            String latitude = cmd.getOptionValue("latitude");
            String longitude = cmd.getOptionValue("longitude");
            String name = cmd.getOptionValue("name");

            try {
                double lat = Double.parseDouble(latitude);
                double lon = Double.parseDouble(longitude);

                if (LoggedUserService.isConsultant() && !LoggedUserService.loggedUser.getUsername().equals(username)) {
                    System.out.println("you cannot change the location of another user");
                } else {
                    User currentUser = userService.findByUsername(username);
                    Location location = currentUser.getLocation();
                    if (location == null) {
                        Location loc = new Location(name, lat, lon, Constants.RED);
                        currentUser.setLocation(loc);
                        userService.update(currentUser);
                    } else {
                        location.setName(name);
                        location.setLatitude(lat);
                        location.setLongitude(lon);
                        userService.update(currentUser);
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println("latitude & longitude must be of type double");
            }
        } else {
            System.out.println("You must login to performs this command");
        }

    }
}
