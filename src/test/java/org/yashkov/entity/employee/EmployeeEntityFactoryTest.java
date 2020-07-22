package org.yashkov.entity.employee;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.yashkov.entity.employee.EmployeeEntityFactory.EmployeeEntity;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class EmployeeEntityFactoryTest {
    @Mock
    private EmployeeRepository repository;

    @InjectMocks
    private EmployeeEntityFactory factory;

    @Test
    void empty_ReturnsDifferentEntity_Always()
    {
        EmployeeEntity ee1 = factory.empty();
        EmployeeEntity ee2 = factory.empty();

        assertThat(ee1).isNotNull();
        assertThat(ee2).isNotNull();
        assertThat(ee2).isNotSameAs(ee1);
    }

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
    class EmptyEntity {
        private EmployeeEntity entity;

        @BeforeEach
        void setUp()
        {
            entity = factory.empty();
        }
    }

    @Nested
    class PresentEntity {
        @Captor
        private ArgumentCaptor<Employee> value;

        private EmployeeEntity entity;

        @BeforeEach
        void setUp()
        {
            entity = factory.instance();
        }

        @Test
        void getManager_ReturnsEmpty_WhenManagerIdIsNotSet()
        {
            assertThat(entity.getManager().isPresent()).isFalse();
        }

        @Test
        void getManager_ReturnsPresentNonPersistedDirty_WhenManagerIdIsSet()
        {
            entity.managerID("1222");

            EmployeeEntity mgr = entity.getManager();

            assertThat(mgr).isNotSameAs(entity);
            assertThat(mgr.isPresent()).isTrue();
            assertThat(mgr.isPersisted()).isFalse();
            assertThat(mgr.isDirty()).isTrue();
            assertThat(mgr.get().getEmployeeID()).isEqualTo("1222");
        }
    }
}
