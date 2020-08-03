package org.yashkov.entity.employee;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;
import static org.mockito.Mockito.doReturn;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.yashkov.entity.SearchCriteria;
import org.yashkov.entity.employee.EmployeeEntityFactory.EmployeeEntity;

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
        private ArgumentCaptor<SearchCriteria> criteria;

        private EmployeeEntity entity;

        @BeforeEach
        void setUp()
        {
            entity = factory.instance();
        }

        @Test
        void getManager_ReturnsEmpty_WhenManagerIdIsNotSet()
        {
            assertThat(entity.getManager()).isEmpty();
        }

        @Test
        void getManager_ReturnsNonPersistedDirty_WhenManagerIdIsSet()
        {
            entity.managerID("1222");

            EmployeeEntity mgr = entity.getManager().get();

            assertThat(mgr).isNotSameAs(entity);
            assertThat(mgr.isPersisted()).isFalse();
            assertThat(mgr.isDirty()).isTrue();
            assertThat(mgr.get().getEmployeeID()).isEqualTo("1222");
        }

        @Test
        void getDirectReports_ReturnsEmptyStream_WhenEmployeeIdIsNotSet()
        {
            assertThat(entity.getDirectReports()).isEmpty();
        }

        @Test
        void getDirectReports_ReturnsEmptyStream_WhenNoDirectReports()
        {
            entity.employeeID("2221");

            assertThat(entity.getDirectReports()).isEmpty();
        }

        @Test
        void getDirectReports_WrapsEmployeesIntoEntities_WhenDirectReports()
        {
            String mgr = "2221";
            Employee e1 = e("1001", mgr);
            Employee e2 = e("1002", mgr);

            doReturn(Stream.of(e1, e2)).when(repository)
                .search(criteria.capture());

            entity.employeeID(mgr);

            assertThat(entity.getDirectReports()).extracting(
                EmployeeEntity::get,
                EmployeeEntity::isDirty,
                EmployeeEntity::isPersisted
            ).containsExactly(
                tuple(e1, true, false),
                tuple(e2, true, false)
            );
            assertThat(criteria.getValue()).hasToString("managerID = 2221");
        }

        private Employee e(String id, String mgr)
        {
            Employee e1 = new Employee();

            e1.setEmployeeID(id);
            e1.setManagerID(mgr);

            return e1;
        }
    }
}
