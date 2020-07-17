package org.yashkov.entity.department;

import org.yashkov.entity.Entity;

public class DepartmentEntityFactory {
    private final DepartmentRepository repository;

    public DepartmentEntityFactory(DepartmentRepository repository)
    {
        this.repository = repository;
    }

    public DepartmentEntity instance()
    {
        return new DepartmentEntity(repository, new Department());
    }

    public static class DepartmentEntity
    extends Entity<ImmutableDepartment, Department, DepartmentEntity> {
        private DepartmentEntity(DepartmentRepository repository,
            Department value)
        {
            super(repository, value);
        }
    }
}
