package org.yashkov.entity.department;

import org.yashkov.entity.DuplicateEntityException;

public class DepartmentEntityFactory {
    private final DepartmentRepository repository;

    public DepartmentEntityFactory(DepartmentRepository repository)
    {
        this.repository = repository;
    }

    public DepartmentEntity instance()
    {
        return new DepartmentEntity();
    }

    public class DepartmentEntity {
        private Department value = new Department();

        private boolean persisted = false;

        private boolean dirty = false;

        public DepartmentEntity load()
        {
            if (dirty) {
                Department nv = repository.load(value);

                if (nv != null) {
                    value = nv;
                    persisted = true;
                    dirty = false;
                }
            }

            return this;
        }

        public DepartmentEntity persist()
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

        public DepartmentEntity delete()
        {
            if (persisted || dirty) {
                repository.delete(value);
                persisted = false;
            }

            return this;
        }

        public ImmutableDepartment get()
        {
            return value;
        }

        public Department modify()
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
