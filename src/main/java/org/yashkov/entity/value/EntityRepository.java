package org.yashkov.entity.value;

import java.util.stream.Stream;

public interface EntityRepository<K, E extends Entity<K, ?, ?>> {
    public E instance(K key);

    public Stream<E> search(SearchCriteria criteria);
}
