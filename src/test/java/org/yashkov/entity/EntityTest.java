package org.yashkov.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EntityTest {
    private TestEntity entity = new TestEntity();

    @Nested
    class NotPersisted {
        @Test
        void constructor_SetsInititalState()
        {
            assertThat(entity.isPersisted()).isFalse();
            assertThat(entity.isDirty()).isFalse();
        }

        @Test
        void markDirty_MarksEntityAsDirty()
        {
            entity.markDirty();

            assertThat(entity.isPersisted()).isFalse();
            assertThat(entity.isDirty()).isTrue();
        }

        @Test
        void load_LoadsData_WhenNotDirty()
        {
            entity.load();

            assertThat(entity.doLoadCount).isEqualTo(1);
            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isFalse();
        }

        @Test
        void load_DoesNothing_WhenDirty()
        {
            entity.markDirty();

            entity.load();

            assertThat(entity.doLoadCount).isEqualTo(0);
            assertThat(entity.isPersisted()).isFalse();
            assertThat(entity.isDirty()).isTrue();
        }

        @Test
        void load_PropagatesException_WhenDoLoadFails()
        {
            entity.doLoadException = new RuntimeException("load");

            assertThatThrownBy(() -> entity.load())
                .isSameAs(entity.doLoadException);

            assertThat(entity.isPersisted()).isFalse();
            assertThat(entity.isDirty()).isFalse();
        }

        @Test
        void refresh_LoadsData_WhenNotDirty()
        {
            entity.refresh();

            assertThat(entity.doLoadCount).isEqualTo(1);
            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isFalse();
        }

        @Test
        void refresh_LoadsData_WhenDirty()
        {
            entity.markDirty();

            entity.refresh();

            assertThat(entity.doLoadCount).isEqualTo(1);
            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isFalse();
        }

        @Test
        void refresh_PropagatesException_WhenDoLoadFails()
        {
            entity.doLoadException = new RuntimeException("load");
            entity.markDirty();

            assertThatThrownBy(() -> entity.refresh())
                .isSameAs(entity.doLoadException);

            assertThat(entity.isPersisted()).isFalse();
            assertThat(entity.isDirty()).isTrue();
        }

        @Test
        void persist_DoesNothing_WhenNotDirty()
        {
            entity.persist();

            assertThat(entity.doPersistCount).isEqualTo(0);
            assertThat(entity.isPersisted()).isFalse();
            assertThat(entity.isDirty()).isFalse();
        }

        @Test
        void persist_PersistsData_WhenDirty()
        {
            entity.markDirty();

            entity.persist();

            assertThat(entity.doPersistCount).isEqualTo(1);
            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isFalse();
        }

        @Test
        void persist_PropagatesException_WhenDoPersistFails()
        {
            entity.doPersistException = new RuntimeException("persist");
            entity.markDirty();

            assertThatThrownBy(() -> entity.persist())
                .isSameAs(entity.doPersistException);

            assertThat(entity.doPersistCount).isEqualTo(1);
            assertThat(entity.isPersisted()).isFalse();
            assertThat(entity.isDirty()).isTrue();
        }
    }

    @Nested
    class Persisted {
        @BeforeEach
        void setUp()
        {
            entity.load();
            entity.doLoadCount = 0;
        }

        @Test
        void markDirty_MarksEntityAsDirty()
        {
            entity.markDirty();

            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isTrue();
        }

        @Test
        void load_DoesNothing_WhenNotDirty()
        {
            entity.load();

            assertThat(entity.doLoadCount).isEqualTo(0);
            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isFalse();
        }

        @Test
        void load_DoesNothing_WhenDirty()
        {
            entity.markDirty();

            entity.load();

            assertThat(entity.doLoadCount).isEqualTo(0);
            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isTrue();
        }

        @Test
        void refresh_LoadsData_WhenNotDirty()
        {
            entity.refresh();

            assertThat(entity.doLoadCount).isEqualTo(1);
            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isFalse();
        }

        @Test
        void refresh_LoadsData_WhenDirty()
        {
            entity.markDirty();

            entity.refresh();

            assertThat(entity.doLoadCount).isEqualTo(1);
            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isFalse();
        }

        @Test
        void refresh_PropagatesException_WhenDoLoadFails()
        {
            entity.doLoadException = new RuntimeException("load");
            entity.markDirty();

            assertThatThrownBy(() -> entity.refresh())
                .isSameAs(entity.doLoadException);

            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isTrue();
        }

        @Test
        void persist_DoesNothing_WhenNotDirty()
        {
            entity.persist();

            assertThat(entity.doPersistCount).isEqualTo(0);
            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isFalse();
        }

        @Test
        void persist_PersistsData_WhenDirty()
        {
            entity.markDirty();

            entity.persist();

            assertThat(entity.doPersistCount).isEqualTo(1);
            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isFalse();
        }

        @Test
        void persist_PropagatesException_WhenDoPersistFails()
        {
            entity.doPersistException = new RuntimeException("persist");
            entity.markDirty();

            assertThatThrownBy(() -> entity.persist())
                .isSameAs(entity.doPersistException);

            assertThat(entity.doPersistCount).isEqualTo(1);
            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isTrue();
        }
    }

    public interface TestModel {
    }

    public static class TestEntity extends Entity implements TestModel {
        public int doLoadCount = 0;

        public RuntimeException doLoadException = null;

        public int doPersistCount = 0;

        public RuntimeException doPersistException = null;

        @Override
        public void doLoad()
        {
            ++doLoadCount;

            if (doLoadException != null)
                throw doLoadException;
        }

        @Override
        public void doPersist()
        {
            ++doPersistCount;

            if (doPersistException != null)
                throw doPersistException;
        }
    }
}
