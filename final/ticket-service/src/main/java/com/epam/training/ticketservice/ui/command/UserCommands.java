package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@RequiredArgsConstructor
public class UserCommands {
    @Autowired
    private final UserService userService;

    @ShellMethod(key = "sign in privileged", value = "Sign in as admin")
    public String signInPrivileged(String username, String password) {
        return userService.signInAdmin(username, password)
                .map(userDto -> "Signed in as " + userDto.username())
                .orElse("Login failed due to incorrect credentials");
    }

    @ShellMethod(key = "sign out", value = "Sign out")
    public String signOut() {
        return userService.signOut()
                .map(userDto -> userDto.username() + " signed out")
                .orElse("You're not logged in");
    }

    @ShellMethod(key = "describe account", value = "Describe your account")
    public String describe() {
        return userService.describe()
                .map(userDto -> "Signed in with privileged account " + userDto.username())
                .orElse("You are not signed in");
    }
}
