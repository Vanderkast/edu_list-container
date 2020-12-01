package com.vanderkast.edu.list_container;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static com.vanderkast.edu.list_container.Helper.*;
import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class DumbConcurrentPlentyKeeper_Init_DevTest {

    @Test
    public void createEmpty() throws Exception {
        var keeper = new DumbConcurrentPlentyKeeper<Integer>(0, null);
        assertArrayEquals(dataListOf(keeper), DumbConcurrentPlentyKeeper.EMPTY);
    }

    @Test
    public void createNotEmpty() throws Exception {
        var cap = 5;
        var list = dataListOf(new DumbConcurrentPlentyKeeper<Integer>(cap, null));

        assertEquals(cap, list.length);
        for (Object o : list) // checks that all elements in list are null;
            assertNull(o);
    }

    @Test
    public void createWithMutex() throws Exception {
        var mutex = new Object();
        var keeper = new DumbConcurrentPlentyKeeper<Integer>(0, mutex);
        assertEquals(mutex, mutexOf(keeper));
    }

    @Test
    public void sizeOnCreated() throws Exception {
        var cap = 5;
        var keeper = new DumbConcurrentPlentyKeeper<Integer>(cap, null);
        assertEquals(0, sizeOf(keeper));
    }
}
