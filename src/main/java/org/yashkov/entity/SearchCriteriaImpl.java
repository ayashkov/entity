package org.yashkov.entity;

import java.util.Objects;

public class SearchCriteriaImpl implements SearchCriteria, OpSelection {
    private String attr = null;

    private String op = null;

    private Object value = null;

    @Override
    public OpSelection with(String attr)
    {
        this.attr = attr;

        return this;
    }

    @Override
    public <T> SearchCriteria equalsTo(T value)
    {
        op = "=";
        this.value = value;

        return this;
    }

    @Override
    public String toString()
    {
        return Objects.toString(attr, "UNDEF") + " " +
            Objects.toString(op, "!") + " " + Objects.toString(value);
    }
}
