package org.yashkov.entity;

public interface Countable<R> {
    int search(SearchCriteria criteria);
}
