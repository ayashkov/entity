package org.yashkov.entity;

import java.util.Optional;

public interface EntityRepository<R, W extends R> {
    default Optional<W> load(R value)
    {
        throw new UnsupportedOperationException("load is not supported");
    }

    default void insert(R value) throws DuplicateEntityException
    {
        throw new UnsupportedOperationException("insert is not supported");
    }

    default boolean update(R value)
    {
        throw new UnsupportedOperationException("load is not supported");
    }

    default void delete(R value)
    {
        throw new UnsupportedOperationException("delete is not supported");
    }
}
