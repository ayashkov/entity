package org.yashkov.entity.inheritance;

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

        refresh();
    }

    public void refresh()
    {
        try {
            doLoad();
            persisted = true;
            dirty = false;
        } catch (NoSuchEntityException ex) {
            persisted = false;
        }
    }

    public void persist()
    {
        if (!dirty)
            return;

        doPersist();
        persisted = true;
        dirty = false;
    }

    protected abstract void doLoad() throws NoSuchEntityException;

    protected abstract void doPersist();
}
