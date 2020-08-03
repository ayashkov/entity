package org.yashkov.entity.employee;

import java.util.Optional;
import java.util.stream.Stream;

import org.yashkov.entity.Entity;
import org.yashkov.entity.SearchCriteriaImpl;

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

        private EmployeeEntity(Employee employee)
        {
            super(repository, employee);
            modify();
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

        public Stream<EmployeeEntity> getDirectReports()
        {
            String id = get().getEmployeeID();

            if (id == null)
                return Stream.empty();

            return repository.search(
                new SearchCriteriaImpl().with("managerID").equalsTo(id))
                .map(EmployeeEntity::new);
        }
    }
}
