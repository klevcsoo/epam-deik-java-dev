package com.epam.training.ticketservice.core;

import java.util.List;
import java.util.Optional;

public interface BaseCrudService<T, ID> {
    void set(T entity);

    void delete(ID id);

    Optional<T> get(ID id);

    List<T> list();
}
