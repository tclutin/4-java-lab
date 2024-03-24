package org.example.accounts;

public class UserProfile {
    private String login;
    private String password;
    private String email;

    public UserProfile(String login, String password, String email) {
        this.login = login;
        this.password = password;
        this.email = email;
    }

    public String getLogin() {
        return this.login;
    }

    public String getPass() {
        return this.password;
    }

    public String getEmail() {
        return this.login;
    }
}
