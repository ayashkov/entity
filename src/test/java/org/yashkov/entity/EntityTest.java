package org.yashkov.entity;

import static org.assertj.core.api.Assertions.assertThat;

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
    }

    public interface TestModel {
    }

    public static class TestEntity extends Entity implements TestModel {
        public int doLoadCount = 0;

        @Override
        public void doLoad()
        {
            ++doLoadCount;
        }

        @Override
        public void doPersist()
        {
            // TODO Auto-generated method stub
        }
    }
}
