package org.yashkov.entity.department;

public class Department implements ImmutableDepartment {
    private String departmentID = null;

    private String name = null;

    private String hierarchy = null;

    @Override
    public String getDepartmentID()
    {
        return departmentID;
    }

    public void setDepartmentID(String id)
    {
        departmentID = id;
    }

    @Override
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String getHierarchy()
    {
        return hierarchy;
    }

    public void setHierarchy(String hierarchy)
    {
        this.hierarchy = hierarchy;
    }
}
