package com.itec.holzfaller.cli.commands.location;

import com.itec.holzfaller.cli.commands.Command;
import com.itec.holzfaller.common.LoggedUserService;
import com.itec.holzfaller.entities.Location;
import com.itec.holzfaller.repository.LocationRepo;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

import java.util.List;

public class ListLocationsCommand implements Command {

    private Options options = new Options();
    private LocationRepo locationRepo = new LocationRepo();

    public ListLocationsCommand() {
        Option allOption = new Option("a", "all", false, "All option");
        options.addOption(allOption);
    }

    @Override
    public Options getOptions() {
        return options;
    }

    @Override
    public void execute(CommandLine cmd) {
        if (LoggedUserService.loggedUser != null) {
            List<Location> locations = locationRepo.findAll();
            System.out.println("Id \t Name");
            locations.forEach(this::printLocation);
        } else {
            System.out.println("You must login to performs this command");
        }
    }

    private void printLocation(Location location) {
        System.out.println(location.getId() + "\t" + location.getName());
    }

}
