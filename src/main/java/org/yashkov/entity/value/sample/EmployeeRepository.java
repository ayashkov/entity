package org.yashkov.entity.value.sample;

public interface EmployeeRepository {
    boolean load(Employee value);

    void insert(Employee value) throws DuplicateEntityException;

    boolean update(Employee value);

    boolean delete(Employee value);
}
