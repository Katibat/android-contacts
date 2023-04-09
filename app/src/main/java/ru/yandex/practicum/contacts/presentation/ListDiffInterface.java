package ru.yandex.practicum.contacts.presentation.base;

public interface ListDiffInterface<T> {

    boolean theSameAs(T t);

    boolean equals(Object o);
}