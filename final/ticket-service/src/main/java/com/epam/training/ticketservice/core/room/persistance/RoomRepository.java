package com.epam.training.ticketservice.core.room.persistance;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, String> { }
