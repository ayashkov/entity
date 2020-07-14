package org.yashkov.entity.value.sample;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.yashkov.entity.value.sample.EmployeeEntityFactory.EmployeeEntity;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class EmployeeEntityFactoryTest {
    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeEntityFactory factory;

    @Test
    void instance_ReturnsDifferentEntity_Always()
    {
        EmployeeEntity ee1 = factory.instance();
        EmployeeEntity ee2 = factory.instance();

        assertThat(ee1).isNotNull();
        assertThat(ee2).isNotNull();
        assertThat(ee2).isNotSameAs(ee1);
    }

    @Nested
    class Entity {
        @Captor
        private ArgumentCaptor<Employee> value;

        private EmployeeEntity entity;

        @BeforeEach
        void setUp()
        {
            entity = factory.instance();
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
            ImmutableEmployee ie1 = entity.get();
            ImmutableEmployee ie2 = entity.get();

            assertThat(ie1).isNotNull();
            assertThat(ie2).isNotNull();
            assertThat(ie2).isSameAs(ie1);
            assertThat(entity.isPersisted()).isFalse();
            assertThat(entity.isDirty()).isFalse();
        }

        @Test
        void modify_MarksDirtyReturnsSameInstance_Always()
        {
            Employee e1 = entity.modify();
            Employee e2 = entity.modify();

            assertThat(e1).isNotNull();
            assertThat(e2).isNotNull();
            assertThat(e2).isSameAs(e1);
        }

        @Test
        void modify_MarksDirty_Always()
        {
            entity.modify();

            assertThat(entity.isPersisted()).isFalse();
            assertThat(entity.isDirty()).isTrue();
        }

        @Test
        void load_LoadsValue_WhenItExists()
        {
            ImmutableEmployee ov = entity.get();
            Employee nv = new Employee();

            doReturn(nv).when(repository).load(value.capture());

            assertThat(entity.load()).isSameAs(entity);

            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isFalse();
            assertThat(value.getValue()).isSameAs(ov);
            assertThat(entity.get()).isSameAs(nv);
        }

        @Test
        void load_ClearsDirty_WhenValueExists()
        {
            entity.modify();

            doReturn(new Employee()).when(repository).load(any());

            assertThat(entity.load()).isSameAs(entity);

            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isFalse();
        }

        @Test
        void load_LeavesNotPersistedAndDirty_WhenValueDoesNotExist()
        {
            ImmutableEmployee ov = entity.get();

            entity.modify();

            doReturn(null).when(repository).load(any());

            assertThat(entity.load()).isSameAs(entity);

            assertThat(entity.isPersisted()).isFalse();
            assertThat(entity.isDirty()).isTrue();
            assertThat(entity.get()).isSameAs(ov);
        }

        @Test
        void load_PropagatesException_WhenRepositoryLoadFails()
        {
            RuntimeException t = new RuntimeException("load");

            entity.modify();

            doThrow(t).when(repository).load(any());

            assertThatThrownBy(() -> entity.load()).isSameAs(t);

            assertThat(entity.isPersisted()).isFalse();
            assertThat(entity.isDirty()).isTrue();
        }

        @Test
        void persist_DoesNothing_WhenNotDirty()
        {
            assertThat(entity.persist()).isSameAs(entity);

            verifyNoInteractions(repository);

            assertThat(entity.isPersisted()).isFalse();
            assertThat(entity.isDirty()).isFalse();
        }

        @Test
        void persist_InsertsValue_WhenDirtyAndNotPersisted() throws Exception
        {
            entity.modify();

            assertThat(entity.persist()).isSameAs(entity);

            verify(repository).insert(value.capture());

            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isFalse();
            assertThat(value.getValue()).isSameAs(entity.get());
        }

        @Test
        void persist_UpdatesValue_WhenFirstInsertIndicatesDuplicate()
            throws Exception
        {
            entity.modify();

            doThrow(new DuplicateEntityException()).when(repository).insert(any());
            doReturn(true).when(repository).update(value.capture());

            assertThat(entity.persist()).isSameAs(entity);

            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isFalse();
            assertThat(value.getValue()).isSameAs(entity.get());
        }

        @Test
        void persist_ThrowsException_WhenSecondUpdateReturnsFalse()
            throws Exception
        {
            entity.modify();

            doThrow(new DuplicateEntityException()).when(repository).insert(any());
            doReturn(false).when(repository).update(any());

            assertThatThrownBy(() -> entity.persist())
                .isInstanceOf(IllegalStateException.class);

            assertThat(entity.isPersisted()).isFalse();
            assertThat(entity.isDirty()).isTrue();
        }

        @Test
        void persist_UpdatesValue_WhenDirtyAndPersisted()
        {
            doReturn(new Employee()).when(repository).load(any());

            entity.load().modify();

            doReturn(true).when(repository).update(value.capture());

            assertThat(entity.persist()).isSameAs(entity);

            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isFalse();
            assertThat(value.getValue()).isSameAs(entity.get());
        }

        @Test
        void persist_InsertsValue_WhenFirstUpdateReturnsFalse()
            throws Exception
        {
            doReturn(new Employee()).when(repository).load(any());

            entity.load().modify();

            doReturn(false).when(repository).update(any());

            assertThat(entity.persist()).isSameAs(entity);

            verify(repository).insert(value.capture());

            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isFalse();
            assertThat(value.getValue()).isSameAs(entity.get());
        }

        @Test
        void persist_ThrowsException_WhenSecondInsertIndicatesDuplicate()
            throws Exception
        {
            doReturn(new Employee()).when(repository).load(any());

            entity.load().modify();

            doReturn(false).when(repository).update(any());
            doThrow(new DuplicateEntityException()).when(repository)
                .insert(any());

            assertThatThrownBy(() -> entity.persist())
                .isInstanceOf(IllegalStateException.class);

            assertThat(entity.isPersisted()).isTrue();
            assertThat(entity.isDirty()).isTrue();
        }
    }
}
