package com.epam.training.ticketservice.core;

import java.util.List;
import java.util.Optional;

public interface BaseCrudService<T> {
    void set(T entity);

    void delete(String id);

    Optional<T> get(String id);

    List<T> list();
}
