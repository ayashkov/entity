package org.yashkov.entity;

import java.util.Optional;

public interface EntityRepository<R, W extends R> {
    Optional<W> load(R value);

    void insert(R value) throws DuplicateEntityException;

    boolean update(R value);

    void delete(R value);
}
