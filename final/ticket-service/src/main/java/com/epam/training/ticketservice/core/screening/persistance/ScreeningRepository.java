package com.epam.training.ticketservice.core.screening.persistance;

import com.epam.training.ticketservice.core.room.persistance.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, ScreeningId> {
    List<Screening> findAllById_Room(Room id_room);
}
