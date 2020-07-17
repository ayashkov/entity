package org.yashkov.entity.employee;

import org.yashkov.entity.Entity;

public class EmployeeEntityFactory {
    private final EmployeeRepository repository;

    public EmployeeEntityFactory(EmployeeRepository repository)
    {
        this.repository = repository;
    }

    public EmployeeEntity instance()
    {
        return new EmployeeEntity(repository, new Employee());
    }

    public static class EmployeeEntity
    extends Entity<ImmutableEmployee, Employee, EmployeeEntity> {
        private EmployeeEntity(EmployeeRepository repository,
            Employee value)
        {
            super(repository, value);
        }
    }
}
