package org.yashkov.entity.value.sample;

import org.yashkov.entity.value.DuplicateEntityException;

public class EmployeeEntityFactory {
    private final EmployeeRepository repository;

    public EmployeeEntityFactory(EmployeeRepository repository)
    {
        this.repository = repository;
    }

    public EmployeeEntity instance()
    {
        return new EmployeeEntity();
    }

    public class EmployeeEntity {
        private Employee value = new Employee();

        private boolean persisted = false;

        private boolean dirty = false;

        public EmployeeEntity load()
        {
            if (dirty) {
                Employee nv = repository.load(value);

                if (nv != null) {
                    value = nv;
                    persisted = true;
                    dirty = false;
                }
            }

            return this;
        }

        public EmployeeEntity persist()
        {
            if (dirty) {
                if (persisted)
                    upsert();
                else
                    indate();

                persisted = true;
                dirty = false;
            }

            return this;
        }

        public EmployeeEntity delete()
        {
            if (persisted || dirty) {
                repository.delete(value);
                persisted = false;
            }

            return this;
        }

        public ImmutableEmployee get()
        {
            return value;
        }

        public Employee modify()
        {
            dirty = true;

            return value;
        }

        public boolean isPersisted()
        {
            return persisted;
        }

        public boolean isDirty()
        {
            return dirty;
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
}
