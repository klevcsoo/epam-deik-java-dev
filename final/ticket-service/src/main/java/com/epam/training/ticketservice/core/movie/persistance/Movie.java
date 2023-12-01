package com.epam.training.ticketservice.core.movie.persistance;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Table(name = "movies")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {

    @Id
    private String title;

    private String genre;

    private Integer length;

    @Override
    public String toString() {
        return getTitle() + " (" + getGenre() + ", " + getLength()
            + " minutes)";
    }
}
