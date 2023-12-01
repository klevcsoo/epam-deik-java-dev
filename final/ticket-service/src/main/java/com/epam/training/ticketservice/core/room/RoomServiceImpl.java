package com.epam.training.ticketservice.core.room;

import com.epam.training.ticketservice.core.room.persistance.Room;
import com.epam.training.ticketservice.core.room.persistance.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService{
    @Autowired
    private final RoomRepository roomRepository;

    @Override
    public void set(Room entity) {
        roomRepository.save(entity);
    }

    @Override
    public void delete(String id) {
        roomRepository.deleteById(id);
    }

    @Override
    public Optional<Room> get(String id) {
        return roomRepository.findById(id);
    }

    @Override
    public List<Room> list() {
        return roomRepository.findAll();
    }
}
