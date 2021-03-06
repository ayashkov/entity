package org.yashkov.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class EntityTest {
    @Mock
    private EntityRepository<ImmutableValue, Value> repository;

    @Captor
    private ArgumentCaptor<Value> value;

    private TestEntity entity;

    @BeforeEach
    void setUp()
    {
        entity = new TestEntity();
    }

    @Test
    void hasDefaultProperties_Initially()
    {
        assertThat(entity.isPersisted()).isFalse();
        assertThat(entity.isDirty()).isFalse();
    }

    @Test
    void get_ReturnsSameInstance_Always()
    {
        ImmutableValue v1 = entity.get();
        ImmutableValue v2 = entity.get();

        assertThat(v1).isNotNull();
        assertThat(v2).isNotNull();
        assertThat(v2).isSameAs(v1);
        assertThat(entity.isPersisted()).isFalse();
        assertThat(entity.isDirty()).isFalse();
    }

    @Test
    void modify_ReturnsSameInstance_Always()
    {
        Value v1 = entity.modify();
        Value v2 = entity.modify();

        assertThat(v1).isNotNull();
        assertThat(v2).isNotNull();
        assertThat(v2).isSameAs(v1);
    }

    @Test
    void modify_MarksDirty_Always()
    {
        entity.modify();

        assertThat(entity.isPersisted()).isFalse();
        assertThat(entity.isDirty()).isTrue();
    }

    @Test
    void load_LoadsAndClearsDirty_WhenDirtyValueExists()
    {
        Value ov = entity.modify();
        Value nv = new Value();

        doReturn(Optional.of(nv)).when(repository).load(value.capture());

        assertThat(entity.load()).isSameAs(entity);

        assertThat(entity.get()).isSameAs(nv);
        assertThat(entity.isPersisted()).isTrue();
        assertThat(entity.isDirty()).isFalse();
        assertThat(value.getValue()).isSameAs(ov);
    }

    @Test
    void load_DoesNothing_WhenNotDirty()
    {
        ImmutableValue ov = entity.get();

        assertThat(entity.load()).isSameAs(entity);

        verifyNoInteractions(repository);

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isFalse();
        assertThat(entity.isDirty()).isFalse();
    }

    @Test
    void load_LeavesNotPersistedAndDirty_WhenValueDoesNotExist()
    {
        Value ov = entity.modify();

        doReturn(Optional.empty()).when(repository).load(any());

        assertThat(entity.load()).isSameAs(entity);

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isFalse();
        assertThat(entity.isDirty()).isTrue();
    }

    @Test
    void load_PropagatesException_WhenRepositoryLoadFails()
    {
        RuntimeException t = new RuntimeException("load");
        Value ov = entity.modify();

        doThrow(t).when(repository).load(any());

        assertThatThrownBy(() -> entity.load()).isSameAs(t);

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isFalse();
        assertThat(entity.isDirty()).isTrue();
    }

    @Test
    void persist_DoesNothing_WhenNotDirty()
    {
        ImmutableValue ov = entity.get();

        assertThat(entity.persist()).isSameAs(entity);

        verifyNoInteractions(repository);

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isFalse();
        assertThat(entity.isDirty()).isFalse();
    }

    @Test
    void persist_InsertsValue_WhenDirtyAndNotPersisted() throws Exception
    {
        Value ov = entity.modify();

        assertThat(entity.persist()).isSameAs(entity);

        verify(repository).insert(value.capture());

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isTrue();
        assertThat(entity.isDirty()).isFalse();
        assertThat(value.getValue()).isSameAs(ov);
    }

    @Test
    void persist_UpdatesValue_WhenFirstInsertIndicatesDuplicate()
        throws Exception
    {
        Value ov = entity.modify();

        doThrow(new DuplicateEntityException()).when(repository).insert(any());
        doReturn(true).when(repository).update(value.capture());

        assertThat(entity.persist()).isSameAs(entity);

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isTrue();
        assertThat(entity.isDirty()).isFalse();
        assertThat(value.getValue()).isSameAs(ov);
    }

    @Test
    void persist_ThrowsException_WhenSecondUpdateReturnsFalse()
        throws Exception
    {
        Value ov = entity.modify();

        doThrow(new DuplicateEntityException()).when(repository).insert(any());
        doReturn(false).when(repository).update(any());

        assertThatThrownBy(() -> entity.persist())
            .isInstanceOf(IllegalStateException.class)
            .hasCauseInstanceOf(DuplicateEntityException.class);

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isFalse();
        assertThat(entity.isDirty()).isTrue();
    }

    @Test
    void persist_PropagatesException_WhenFirstInsertFails() throws Exception
    {
        RuntimeException t = new RuntimeException("insert");
        Value ov = entity.modify();

        doThrow(t).when(repository).insert(any());

        assertThatThrownBy(() -> entity.persist()).isSameAs(t);

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isFalse();
        assertThat(entity.isDirty()).isTrue();
    }

    @Test
    void persist_PropagatesException_WhenSecondUpdateFails() throws Exception
    {
        RuntimeException t = new RuntimeException("update");
        Value ov = entity.modify();

        doThrow(new DuplicateEntityException()).when(repository).insert(any());
        doThrow(t).when(repository).update(any());

        assertThatThrownBy(() -> entity.persist()).isSameAs(t);

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isFalse();
        assertThat(entity.isDirty()).isTrue();
    }

    @Test
    void persist_UpdatesValue_WhenDirtyAndPersisted()
    {
        entity.modify();

        doReturn(Optional.of(new Value())).when(repository).load(any());

        Value ov = entity.load().modify();

        doReturn(true).when(repository).update(value.capture());

        assertThat(entity.persist()).isSameAs(entity);

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isTrue();
        assertThat(entity.isDirty()).isFalse();
        assertThat(value.getValue()).isSameAs(ov);
    }

    @Test
    void persist_InsertsValue_WhenFirstUpdateReturnsFalse() throws Exception
    {
        entity.modify();

        doReturn(Optional.of(new Value())).when(repository).load(any());

        Value ov = entity.load().modify();

        doReturn(false).when(repository).update(any());

        assertThat(entity.persist()).isSameAs(entity);

        verify(repository).insert(value.capture());

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isTrue();
        assertThat(entity.isDirty()).isFalse();
        assertThat(value.getValue()).isSameAs(ov);
    }

    @Test
    void persist_ThrowsException_WhenSecondInsertIndicatesDuplicate()
        throws Exception
    {
        entity.modify();

        doReturn(Optional.of(new Value())).when(repository).load(any());

        Value ov = entity.load().modify();

        doReturn(false).when(repository).update(any());
        doThrow(new DuplicateEntityException()).when(repository).insert(any());

        assertThatThrownBy(() -> entity.persist())
            .isInstanceOf(IllegalStateException.class)
            .hasCauseInstanceOf(DuplicateEntityException.class);

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isTrue();
        assertThat(entity.isDirty()).isTrue();
    }

    @Test
    void persist_PropagatesException_WhenFirstUpdateFails()
    {
        entity.modify();

        doReturn(Optional.of(new Value())).when(repository).load(any());

        Value ov = entity.load().modify();
        RuntimeException t = new RuntimeException("update");

        doThrow(t).when(repository).update(any());

        assertThatThrownBy(() -> entity.persist()).isSameAs(t);

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isTrue();
        assertThat(entity.isDirty()).isTrue();
    }

    @Test
    void persist_PropagatesException_WhenSecondInsertFails() throws Exception
    {
        entity.modify();

        doReturn(Optional.of(new Value())).when(repository).load(any());

        Value ov = entity.load().modify();
        RuntimeException t = new RuntimeException("insert");

        doReturn(false).when(repository).update(any());
        doThrow(t).when(repository).insert(any());

        assertThatThrownBy(() -> entity.persist()).isSameAs(t);

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isTrue();
        assertThat(entity.isDirty()).isTrue();
    }

    @Test
    void delete_DoesNothing_WhenNotPersistedAndNotDirty()
    {
        ImmutableValue ov = entity.get();

        assertThat(entity.delete()).isSameAs(entity);

        verifyNoInteractions(repository);

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isFalse();
        assertThat(entity.isDirty()).isFalse();
    }

    @Test
    void delete_Deletes_WhenDirty()
    {
        Value ov = entity.modify();

        assertThat(entity.delete()).isSameAs(entity);

        verify(repository).delete(value.capture());

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isFalse();
        assertThat(entity.isDirty()).isTrue();
        assertThat(value.getValue()).isSameAs(ov);
    }

    @Test
    void delete_Deletes_WhenLoaded()
    {
        entity.modify();

        doReturn(Optional.of(new Value())).when(repository).load(any());

        ImmutableValue ov = entity.load().get();

        assertThat(entity.delete()).isSameAs(entity);

        verify(repository).delete(value.capture());

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isFalse();
        assertThat(entity.isDirty()).isFalse();
        assertThat(value.getValue()).isSameAs(ov);
    }

    @Test
    void delete_PropagatesException_WhenRepositoryDeleteFails()
    {
        entity.modify();

        doReturn(Optional.of(new Value())).when(repository).load(any());

        RuntimeException t = new RuntimeException("delete");
        ImmutableValue ov = entity.load().get();

        doThrow(t).when(repository).delete(any());

        assertThatThrownBy(() -> entity.delete()).isSameAs(t);

        assertThat(entity.get()).isSameAs(ov);
        assertThat(entity.isPersisted()).isTrue();
        assertThat(entity.isDirty()).isFalse();
    }

    private interface ImmutableValue {
    }

    private static class Value implements ImmutableValue {
    }

    private class TestEntity extends Entity<ImmutableValue, Value, TestEntity> {
        public TestEntity()
        {
            super(repository, new Value());
        }
    }
}
