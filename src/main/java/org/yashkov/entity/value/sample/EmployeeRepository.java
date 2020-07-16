package org.yashkov.entity.value.sample;

public interface EmployeeRepository {
    Employee load(ImmutableEmployee value);

    void insert(ImmutableEmployee value) throws DuplicateEntityException;

    boolean update(ImmutableEmployee value);

    void delete(ImmutableEmployee value);
}
