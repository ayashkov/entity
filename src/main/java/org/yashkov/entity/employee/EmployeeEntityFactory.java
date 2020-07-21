package org.yashkov.entity.employee;

import java.util.Optional;

import org.yashkov.entity.Entity;

public class EmployeeEntityFactory {
    private final EmployeeRepository repository;

    public EmployeeEntityFactory(EmployeeRepository repository)
    {
        this.repository = repository;
    }

    public EmployeeEntity instance()
    {
        return instance(new Employee());
    }

    public EmployeeEntity instance(Employee value)
    {
        return new EmployeeEntity(value);
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

        public Optional<EmployeeEntity> getManager()
        {
            String id = get().getManagerID();

            return id == null ? Optional.empty() :
                Optional.of(instance().employeeID(id));
        }
    }
}
