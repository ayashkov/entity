package org.yashkov.persistance.memory;

import java.util.Optional;
import java.util.stream.Stream;

import org.yashkov.entity.DuplicateEntityException;
import org.yashkov.entity.SearchCriteria;
import org.yashkov.entity.employee.Employee;
import org.yashkov.entity.employee.EmployeeRepository;
import org.yashkov.entity.employee.ImmutableEmployee;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Override
    public Optional<Employee> load(ImmutableEmployee value)
    {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public void insert(ImmutableEmployee value)
        throws DuplicateEntityException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean update(ImmutableEmployee value)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void delete(ImmutableEmployee value)
    {
        // TODO Auto-generated method stub
    }

    @Override
    public Stream<Employee> search(SearchCriteria<ImmutableEmployee> criteria)
    {
        // TODO Auto-generated method stub
        return null;
    }
}
