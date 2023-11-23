package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InMemoryAuthenticationProvider implements AuthenticationProvider {
    private final List<User> users;

    public InMemoryAuthenticationProvider() {
        this.users = new ArrayList<>();
    }

    @Override
    public String getUsernameByLoginAndPassword(String login, String password) {
        for (User user : users) {
            if (Objects.equals(user.getPassword(), password) && Objects.equals(user.getLogin(), login)) {
                return user.getUsername();
            }
        }
        return null;
    }

    @Override
    public synchronized boolean register(String login, String password, String username) {
        for (User user : users) {
            if (Objects.equals(user.getUsername(), username) && Objects.equals(user.getLogin(), login)) {
                return false;
            }
        }
        users.add(new User(login, password, username, Role.USER));
        return true;
    }

    @Override
    public synchronized void kick(String userName) {
        users.removeIf(user -> user.getUsername().equals(userName));
    }


    @Override
    public synchronized boolean isAdmin(String username) {
        for (User user : users) {
            if (user.getRole().equals(Role.ADMIN) && user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void setMetAdmin(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)){
                user.setRole(Role.ADMIN);
            }
        }
    }

}
