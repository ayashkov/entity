package org.yashkov.entity;

import java.util.Optional;

public abstract class Entity<R, W extends R, E extends Entity<R, W, E>> {
    private final EntityRepository<R, W> repository;

    private W value;

    private boolean persisted = false;

    private boolean dirty = false;

    public Entity(EntityRepository<R, W> repository, W value)
    {
        this.repository = repository;
        this.value = value;
    }

    public boolean isPersisted()
    {
        return persisted;
    }

    public boolean isDirty()
    {
        return dirty;
    }

    public R get()
    {
        return value;
    }

    public W modify()
    {
        dirty = true;

        return value;
    }

    @SuppressWarnings("unchecked")
    public E load()
    {
        if (dirty) {
            Optional<W> nv = repository.load(value);

            if (nv.isPresent()) {
                value = nv.get();
                persisted = true;
                dirty = false;
            }
        }

        return (E)this;
    }

    @SuppressWarnings("unchecked")
    public E persist()
    {
        if (dirty) {
            if (persisted)
                upsert();
            else
                indate();

            persisted = true;
            dirty = false;
        }

        return (E)this;
    }

    @SuppressWarnings("unchecked")
    public E delete()
    {
        if (persisted || dirty) {
            repository.delete(value);
            persisted = false;
        }

        return (E)this;
    }

    private void indate()
    {
        try {
            repository.insert(value);
        } catch (DuplicateEntityException ex) {
            if (!repository.update(value))
                throw new IllegalStateException("indate failed", ex);
        }
    }

    private void upsert()
    {
        if (!repository.update(value))
            try {
                repository.insert(value);
            } catch (DuplicateEntityException ex) {
                throw new IllegalStateException("upsert failed", ex);
            }
    }
}
