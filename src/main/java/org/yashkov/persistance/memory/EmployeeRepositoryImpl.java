package org.yashkov.persistance.memory;

import org.yashkov.entity.DuplicateEntityException;
import org.yashkov.entity.employee.Employee;
import org.yashkov.entity.employee.EmployeeRepository;
import org.yashkov.entity.employee.ImmutableEmployee;

public class EmployeeRepositoryImpl implements EmployeeRepository {
    @Override
    public Employee load(ImmutableEmployee value)
    {
        // TODO Auto-generated method stub
        return null;
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
}
