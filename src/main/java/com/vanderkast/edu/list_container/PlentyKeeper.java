package com.vanderkast.edu.list_container;

public interface PlentyKeeper<T> {
    void add(T t);

    T get(int index);

    void remove(int index);

    int size();
}
