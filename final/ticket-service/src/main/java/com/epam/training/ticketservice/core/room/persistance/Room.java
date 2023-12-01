package com.epam.training.ticketservice.core.room.persistance;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "rooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {

    @Id
    private String name;

    private Integer rows;

    private Integer seatsPerRow;

    @Override
    public String toString() {
        return "Room " + getName() + " with " + getSeatsPerRow() * getRows() + " seats, "
            + getRows() + " rows and " + getSeatsPerRow() + " columns";
    }
}
