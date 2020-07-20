package org.yashkov.entity;

import java.util.stream.Stream;

public interface Searchable<R, W extends R> {
    Stream<W> search(SearchCriteria<R> criteria);
}
