package org.yashkov.entity.employee;

import org.yashkov.entity.Entity;

public class EmployeeEntityFactory {
    private final EmployeeRepository repository;

    public EmployeeEntityFactory(EmployeeRepository repository)
    {
        this.repository = repository;
    }

    public EmployeeEntity empty()
    {
        return new EmployeeEntity(null);
    }

    public EmployeeEntity instance()
    {
        return new EmployeeEntity(new Employee());
    }

    public class EmployeeEntity
        extends Entity<ImmutableEmployee, Employee, EmployeeEntity> {
        private EmployeeEntity(Employee value)
        {
            super(repository, value);
        }

        public EmployeeEntity employeeID(String id)
        {
            modify().setEmployeeID(id);

            return this;
        }

        public EmployeeEntity managerID(String id)
        {
            modify().setManagerID(id);

            return this;
        }

        public EmployeeEntity getManager()
        {
            String id = get().getManagerID();

            return id == null ? empty() : instance().employeeID(id);
        }
    }
}
