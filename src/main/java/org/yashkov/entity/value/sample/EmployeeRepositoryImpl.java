package org.yashkov.entity.value.sample;

import org.yashkov.entity.value.DuplicateEntityException;

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
