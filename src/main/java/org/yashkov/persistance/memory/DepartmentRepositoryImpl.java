package org.yashkov.persistance.memory;

import java.util.Optional;

import org.yashkov.entity.DuplicateEntityException;
import org.yashkov.entity.department.Department;
import org.yashkov.entity.department.DepartmentRepository;
import org.yashkov.entity.department.ImmutableDepartment;

public class DepartmentRepositoryImpl implements DepartmentRepository {
    @Override
    public Optional<Department> load(ImmutableDepartment value)
    {
        // TODO Auto-generated method stub
        return Optional.empty();
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
