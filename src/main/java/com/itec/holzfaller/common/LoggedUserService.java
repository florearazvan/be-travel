package com.itec.holzfaller.common;

import com.itec.holzfaller.entities.Role;
import com.itec.holzfaller.entities.User;

public class LoggedUserService {

    public static User loggedUser;

    public static boolean isAdmin() {
        return loggedUser.getRole() == Role.ADMIN;
    }

    public static boolean isConsultant() {
        return loggedUser.getRole() == Role.CONSULTANT;
    }

}
