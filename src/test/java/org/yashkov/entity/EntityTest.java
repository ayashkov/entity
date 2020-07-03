package org.yashkov.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class EntityTest {
    private TestEntity entity = new TestEntity();

    @Nested
    class NewEntity {
        @Test
        void constructor_SetsInititalState_Always()
        {
            assertThat(entity.isNew()).isTrue();
            assertThat(entity.isDirty()).isFalse();
        }

        @Test
        void load_LoadsData_WhenNotYetLoaded()
        {
            entity.load();

            assertThat(entity.loaded).isEqualTo(1);
            assertThat(entity.isNew()).isFalse();
            assertThat(entity.isDirty()).isFalse();
        }
    }

    @Nested
    class LoadedEntity {
        @BeforeEach
        void setUp()
        {
            entity.load();
            entity.loaded = 0;
        }

        @Test
        void load_DoesNothing_WhenAlreadyLoaded()
        {
            entity.load();

            assertThat(entity.loaded).isEqualTo(0);
            assertThat(entity.isNew()).isFalse();
            assertThat(entity.isDirty()).isFalse();
        }
    }

    public interface TestModel {
    }

    public static class TestEntity extends Entity implements TestModel {
        public int loaded = 0;

        @Override
        public void doLoad()
        {
            ++loaded;
        }

        @Override
        public void doPersist()
        {
            // TODO Auto-generated method stub
        }
    }
}
