package com.example.vneurochka.models;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel {
    private static List<User> users = new ArrayList<User>();

    public static void addUser(User user){ //ПРОВЕРЯЕТ ПАРОЛЬ
        if ( !(users.contains(user)) ) {
            users.add(user);
        }
    }

    public static List<User> getUsers() {
        return users;
    }

}
