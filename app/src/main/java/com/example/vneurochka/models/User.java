package com.example.vneurochka.models;

public class User {
    public String e_mail;
    public String login;
    public String password;
    public User(String login, String password) {
        this.e_mail = "";
        this.password = password;
        this.login = login;
    }
    public User(String login, String password, String e_mail) {
        this.e_mail = e_mail;
        this.password = password;
        this.login = login;
    }
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
