package org.yashkov.entity.value.sample;

public class Employee implements ImmutableEmployee {
    private Integer id = null;

    private String employeeID = null;

    private String sn = null;

    private String givenName = null;

    @Override
    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    @Override
    public String getEmployeeID()
    {
        return employeeID;
    }

    public void setEmployeeID(String employeeID)
    {
        this.employeeID = employeeID;
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
}
