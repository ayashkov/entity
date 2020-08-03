package org.yashkov.entity;

import java.util.stream.Stream;

public interface Searchable<W> {
    Stream<W> search(SearchCriteria criteria);
}
