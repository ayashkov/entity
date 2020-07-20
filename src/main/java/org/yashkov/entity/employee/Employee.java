package org.yashkov.entity.employee;

public class Employee implements ImmutableEmployee {
    private String employeeID = null;

    private String sn = null;

    private String givenName = null;

    private String managerID = null;

    @Override
    public String getEmployeeID()
    {
        return employeeID;
    }

    public void setEmployeeID(String id)
    {
        employeeID = id;
    }

    @Override
    public String getSn()
    {
        return sn;
    }

    public void setSn(String name)
    {
        sn = name;
    }

    @Override
    public String getGivenName()
    {
        return givenName;
    }

    public void setGivenName(String name)
    {
        givenName = name;
    }

    @Override
    public String getManagerID()
    {
        return managerID;
    }

    public void setManagerID(String id)
    {
        managerID = id;
    }
}
