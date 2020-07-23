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
        return new EmployeeEntity();
    }

    public class EmployeeEntity
        extends Entity<ImmutableEmployee, Employee, EmployeeEntity> {
        private EmployeeEntity()
        {
            super(repository, new Employee());
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
