package com.itec.holzfaller.cli;


import java.util.Arrays;
import java.util.List;

public class Commands {

    public static final String EXIT = "exit";
    public static final String LOGIN = "login";
    public static final String EDIT_USER = "edit-user";
    public static final String LIST_USERS = "list";
    public static final String EDIT_LOCATION = "set-location";
    public static final String ADD_USER = "add-user";
    public static final String DELETE_USER = "delete-user";
    public static final String ADD_JOURNEY = "add-journey";
    public static final String DELETE_JOURNEY = "delete-journey";
    public static final String LIST_JOURNEYS = "list-journey";
    public static final String LIST_LOCATIONS = "list-location";

    public static final List<String> ALL_COMMANDS = Arrays.asList(EXIT, LOGIN, EDIT_USER, LIST_USERS,
            EDIT_LOCATION, ADD_USER, DELETE_USER, ADD_JOURNEY, DELETE_JOURNEY, LIST_JOURNEYS, LIST_LOCATIONS);

}
