package org.yashkov.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EntityTest {
    private TestEntity entity = new TestEntity();

    @Test
    void load_LoadsData_WhenNotYetLoaded()
    {
        assertThat(entity.isNew()).isTrue();
        assertThat(entity.isDirty()).isFalse();

        entity.load();

        assertThat(entity.loaded).isEqualTo(1);
        assertThat(entity.isNew()).isFalse();
        assertThat(entity.isDirty()).isFalse();
    }

    @Test
    void load_DoesNothing_WhenAlreadyLoaded()
    {
        entity.load();

        entity.load();

        assertThat(entity.loaded).isEqualTo(1);
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
