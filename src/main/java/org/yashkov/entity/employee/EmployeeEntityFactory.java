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
//
//        public EmployeeEntity employeeID(String id)
//        {
//            modify().setEmployeeID(id);
//
//            return this;
//        }
//
//        public EmployeeEntity getManager()
//        {
//            return instance().employeeID(get().getManagerID());
//        }
    }
}
