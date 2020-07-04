package org.yashkov.entity;

public abstract class Entity {
    private boolean persisted = false;

    private boolean dirty = false;

    public boolean isPersisted()
    {
        return persisted;
    }

    public boolean isDirty()
    {
        return dirty;
    }

    public void markDirty()
    {
        dirty = true;
    }

    public void load()
    {
        if (persisted || dirty)
            return;

        doLoad();
        persisted = true;
    }

    public abstract void doLoad();

    public abstract void doPersist();
}
