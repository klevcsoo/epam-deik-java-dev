package com.epam.training.ticketservice.core.user.model;

import com.epam.training.ticketservice.core.user.persistance.User;

public record UserDto(String username, User.Role role) { }
