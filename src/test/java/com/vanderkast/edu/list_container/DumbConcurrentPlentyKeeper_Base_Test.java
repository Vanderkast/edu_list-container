package com.vanderkast.edu.list_container;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class DumbConcurrentPlentyKeeper_Base_Test {
    @Test
    public void addToEmpty() {
        var keeper = new DumbConcurrentPlentyKeeper<Integer>(5, null);
        var expected = 11;
        keeper.add(expected);
        assertEquals(expected, (int) keeper.get(0));
    }

    @Test
    public void addTwice() {
        var keeper = new DumbConcurrentPlentyKeeper<Integer>(5, null);
        var first = 11;
        var second = 19;

        keeper.add(first);
        keeper.add(second);

        assertEquals(first, (int) keeper.get(0));
        assertEquals(second, (int) keeper.get(1));
    }

    @Test
    public void addToEmpty_Remove() {
        var keeper = new DumbConcurrentPlentyKeeper<Integer>(5, null);
        keeper.add(11);
        assertNotNull(keeper.get(0));

        keeper.remove(0);

        assertEquals(0, keeper.size());
    }

    @Test
    public void addTwice_RemoveFirst() {
        var keeper = new DumbConcurrentPlentyKeeper<Integer>(5, null);
        keeper.add(11);
        var expected = 19;
        keeper.add(19);
        assertEquals(2, keeper.size());

        keeper.remove(0);
        assertEquals(1, keeper.size());
        assertEquals(expected, (int) keeper.get(0));
    }

    @Test
    public void addOnEmpty_GetOutOfRange() {
        var keeper = new DumbConcurrentPlentyKeeper<Integer>(5, null);
        keeper.add(11);
        try {
            keeper.get(3);
            fail();
        } catch (IndexOutOfBoundsException e) {
            // intended behavior
        }
    }
}
