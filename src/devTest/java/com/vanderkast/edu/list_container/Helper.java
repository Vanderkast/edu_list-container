package com.vanderkast.edu.list_container;

import java.lang.reflect.Field;

import static org.junit.Assert.assertNotNull;

public final class Helper {
    public static Object[] dataListOf(DumbConcurrentPlentyKeeper<?> keeper) throws NoSuchFieldException, IllegalAccessException {
        Field data = DumbConcurrentPlentyKeeper.class.getDeclaredField("data");
        data.setAccessible(true);
        var list = (Object[]) data.get(keeper);
        assertNotNull(list);
        return list;
    }

    public static Object mutexOf(DumbConcurrentPlentyKeeper<?> keeper) throws NoSuchFieldException, IllegalAccessException {
        Field data = DumbConcurrentPlentyKeeper.class.getDeclaredField("mutex");
        data.setAccessible(true);
        var mutex = data.get(keeper);
        assertNotNull(mutex);
        return mutex;
    }

    public static int sizeOf(DumbConcurrentPlentyKeeper<?> keeper) throws NoSuchFieldException, IllegalAccessException {
        Field data = DumbConcurrentPlentyKeeper.class.getDeclaredField("size");
        data.setAccessible(true);
        return (int) data.get(keeper);
    }
}
