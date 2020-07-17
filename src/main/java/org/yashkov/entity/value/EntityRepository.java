package org.yashkov.entity.value;

public interface EntityRepository<R, W extends R> {
    W load(R value);

    void insert(R value) throws DuplicateEntityException;

    boolean update(R value);

    void delete(R value);
}
