package com.epam.training.ticketservice.core.user;

import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistance.User;
import com.epam.training.ticketservice.core.user.persistance.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    private UserDto loggedInUser = null;

    @Override
    public Optional<UserDto> signIn(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isEmpty()) {
            return Optional.empty();
        }
        loggedInUser = new UserDto(user.get().getUsername(), user.get().getRole());
        return describe();
    }

    @Override
    public Optional<UserDto> signInAdmin(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username, password);
        if (user.isEmpty() || user.get().getRole() != User.Role.ADMIN) {
            return Optional.empty();
        }
        loggedInUser = new UserDto(user.get().getUsername(), user.get().getRole());
        return describe();
    }

    @Override
    public Optional<UserDto> signOut() {
        Optional<UserDto> previouslyLoggedInUser = describe();
        loggedInUser = null;
        return previouslyLoggedInUser;
    }

    @Override
    public Optional<UserDto> describe() {
        return Optional.ofNullable(loggedInUser);
    }

    @Override
    public void registerUser(String username, String password) {
        User user = new User(username, password, User.Role.USER);
        userRepository.save(user);
    }
}
