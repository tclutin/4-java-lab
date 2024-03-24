package org.example.accounts;

import java.util.HashMap;
import java.util.Map;

public class AccountService {
    private static Map<String, UserProfile> loginToProfile = new HashMap<>();

    static {
        loginToProfile.put("admin", new UserProfile("admin", "1", "email@email.ru"));
    }

    public static void AddNewUser(UserProfile user) {
        loginToProfile.put(user.getLogin(), user);
    }

    public static UserProfile getUserByLogin(String login) {
        return loginToProfile.get(login);
    }

}
