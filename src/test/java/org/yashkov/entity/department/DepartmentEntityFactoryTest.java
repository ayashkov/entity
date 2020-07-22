package org.yashkov.entity.department;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.yashkov.entity.department.DepartmentEntityFactory.DepartmentEntity;

@MockitoSettings(strictness = Strictness.STRICT_STUBS)
class DepartmentEntityFactoryTest {
    @Mock
    private DepartmentRepository repository;

    @InjectMocks
    private DepartmentEntityFactory factory;

    @Test
    void instance_ReturnsDifferentEntity_Always()
    {
        DepartmentEntity ee1 = factory.instance();
        DepartmentEntity ee2 = factory.instance();

        assertThat(ee1).isNotNull();
        assertThat(ee2).isNotNull();
        assertThat(ee2).isNotSameAs(ee1);
    }
}
