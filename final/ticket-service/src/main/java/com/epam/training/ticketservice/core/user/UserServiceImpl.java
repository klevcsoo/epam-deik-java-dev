package com.epam.training.ticketservice.core.user;

import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistance.User;
import com.epam.training.ticketservice.core.user.persistance.User.Role;
import com.epam.training.ticketservice.core.user.persistance.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    private UserDto loggedInUser = null;

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
    public void requireAuthentication() throws UnauthenticatedException {
        if (loggedInUser == null) {
            throw new UnauthenticatedException();
        }
    }

    @Override
    public void requireAuthorization(Role role)
        throws UnauthenticatedException, UnauthorizedException {
        requireAuthentication();
        if (loggedInUser.role() != role) {
            throw new UnauthorizedException();
        }
    }
}
