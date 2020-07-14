package org.yashkov.entity.value.sample;

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
        private final Employee value = new Employee();

        private boolean persisted = false;

        private boolean dirty = false;

        public EmployeeEntity load()
        {
            if (repository.load(value)) {
                persisted = true;
                dirty = false;
            }

            return this;
        }

        public EmployeeEntity persist()
        {
            if (dirty)
                if (persisted ? upsert() : indate()) {
                    persisted = true;
                    dirty = false;
                }

            return this;
        }

        public EmployeeEntity delete()
        {
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

        private boolean indate()
        {
            try {
                repository.insert(value);
            } catch (DuplicateEntityException ex) {
                if (!repository.update(value))
                    return false;
            }

            return true;
        }

        private boolean upsert()
        {
            if (!repository.update(value))
                try {
                    repository.insert(value);
                } catch (DuplicateEntityException ex) {
                    return false;
                }

            return true;
        }
    }
}
