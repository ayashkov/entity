package org.yashkov.persistance.memory;

import org.yashkov.entity.DuplicateEntityException;
import org.yashkov.entity.department.Department;
import org.yashkov.entity.department.DepartmentRepository;
import org.yashkov.entity.department.ImmutableDepartment;

public class DepartmentRepositoryImpl implements DepartmentRepository {
    @Override
    public Department load(ImmutableDepartment value)
    {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void insert(ImmutableDepartment value)
        throws DuplicateEntityException
    {
        // TODO Auto-generated method stub
    }

    @Override
    public boolean update(ImmutableDepartment value)
    {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void delete(ImmutableDepartment value)
    {
        // TODO Auto-generated method stub
    }
}
