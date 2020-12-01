package com.vanderkast.edu.list_container;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(JUnit4.class)
public class DumbConcurrentPlentyKeeper_Init_Test {
    @Test
    public void sizeOnCreate() {
        int cap = 5;
        var keeper = new DumbConcurrentPlentyKeeper<Integer>(cap, null);
        assertEquals(0, keeper.size());
    }

    @Test
    public void getOnEmpty() {
        var keeper = new DumbConcurrentPlentyKeeper<Integer>(0, null);
        try {
            keeper.get(1);
            fail();
        } catch (IndexOutOfBoundsException e) {
            // intended behavior
        }
    }
}
