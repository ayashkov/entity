package org.yashkov.persistance.memory;

import java.util.stream.Stream;

import org.yashkov.entity.SearchCriteria;
import org.yashkov.entity.employee.Employee;
import org.yashkov.entity.employee.EmployeeRepository;
import org.yashkov.entity.employee.ImmutableEmployee;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Override
    public Stream<Employee> search(SearchCriteria<ImmutableEmployee> criteria)
    {
        // TODO Auto-generated method stub
        return Stream.empty();
    }
}
