package org.yashkov.entity;

public interface Countable<R> {
    int search(SearchCriteria<R> criteria);
}
