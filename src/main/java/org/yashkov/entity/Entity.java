package org.yashkov.entity;

public abstract class Entity {
    private boolean newEntity = true;

    public boolean isNew()
    {
        return newEntity;
    }

    public boolean isDirty()
    {
        return false;
    }

    public void load()
    {
        if (!newEntity)
            return;

        doLoad();
        newEntity = false;
    }

    public abstract void doLoad();

    public abstract void doPersist();
}
