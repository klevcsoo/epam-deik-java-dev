package com.epam.training.ticketservice.core.user;

import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistance.User;
import java.util.Optional;

public interface UserService {

    Optional<UserDto> signInAdmin(String username, String password);

    Optional<UserDto> signOut();

    Optional<UserDto> describe();

    void requireAuthentication() throws UnauthenticatedException;

    void requireAuthorization(User.Role role)
        throws UnauthenticatedException, UnauthorizedException;
}
