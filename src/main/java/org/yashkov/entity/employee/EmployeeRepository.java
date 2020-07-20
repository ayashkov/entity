package org.yashkov.entity.employee;

import org.yashkov.entity.EntityRepository;
import org.yashkov.entity.Searchable;

public interface EmployeeRepository
    extends EntityRepository<ImmutableEmployee, Employee>,
    Searchable<ImmutableEmployee, Employee> {
}
