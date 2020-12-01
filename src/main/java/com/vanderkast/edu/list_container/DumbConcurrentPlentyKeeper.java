package com.vanderkast.edu.list_container;

import java.util.Arrays;
import java.util.Objects;

public class DumbConcurrentPlentyKeeper<T> implements PlentyKeeper<T> {
    public static final Object[] EMPTY = {};
    public static final float SCALE = 1.5f;

    private final Object mutex;

    private volatile int size;

    private Object[] data;

    /**
     * Constructs an empty data list with preset capacity and mutex if passed.
     *
     * @param initialCapacity the initial capacity of the stored data list
     * @param mutex           the object that will be used to sync on
     * @throws IllegalArgumentException if capacity is negative
     */
    public DumbConcurrentPlentyKeeper(int initialCapacity, Object mutex) {
        if (initialCapacity > 0)
            this.data = new Object[initialCapacity];
        else if (initialCapacity == 0)
            this.data = EMPTY;
        else
            throw new IllegalArgumentException("Illegal initial capacity: %s" + initialCapacity);

        this.mutex = mutex == null ? new Object() : mutex;
        this.size = 0;
    }

    /**
     * Expands data list capacity.
     * New capacity calculated as data.length * SCALE
     */
    private void grow() {
        if (size >= 0) {
            var box = new Object[(int) (data.length * SCALE)];
            System.arraycopy(data, 0, box, 0, size);
            data = box;
        }
    }

    /**
     * Adds passed t at the end of data list.
     * Grows data list capacity if size is equal to data list length
     *
     * @param t the object that should be added to data list
     */
    @Override
    public void add(T t) {
        synchronized (mutex) {
            if (size == data.length)
                grow();
            data[size++] = t;
        }
    }

    /**
     * Returns the element at the specified position in data list.
     *
     * @param index of the element to return
     * @return the object is placed in data list at passed index
     */
    @SuppressWarnings("unchecked")
    @Override
    public T get(int index) {
        synchronized (mutex) {
            if (inRange(index))
                return (T) data[index];
            else throw new IndexOutOfBoundsException("Passed illegal index: " + index);
        }
    }

    /**
     * Checks that passed index is in data list range.
     *
     * @param index that should be checked
     * @return true if index presented in data list,
     * false otherwise
     */
    private boolean inRange(int index) {
        return index >= 0 && index < size;
    }

    /**
     * Removes object at passed index.
     * Moves all right-side placed objects left on 1 position.
     *
     * @param index at object should be removed
     */
    @Override
    public void remove(int index) {
        synchronized (mutex) {
            if (!inRange(index))
                throw new IndexOutOfBoundsException("Passed illegal index: " + index);
            if (size == 1)
                this.data = EMPTY;
            else if (size - 1 - index >= 0)
                System.arraycopy(data, index + 1, data, index, size - 1 - index);
            size -= 1;
        }
    }

    /**
     * @return size of stored data list
     */
    @Override
    public int size() {
        synchronized (mutex) {
            return size;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DumbConcurrentPlentyKeeper<?> that = (DumbConcurrentPlentyKeeper<?>) o;
        return size == that.size &&
                mutex.equals(that.mutex) &&
                Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(mutex, size);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }
}
