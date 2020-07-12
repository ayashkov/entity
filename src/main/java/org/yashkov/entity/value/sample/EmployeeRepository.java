package org.yashkov.entity.value.sample;

public interface EmployeeRepository {
    boolean load(Employee value);

    boolean insert(Employee value);

    boolean update(Employee value);

    boolean delete(Employee value);
}
