package com.vanderkast.edu.list_container;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class DumbConcurrentPlentyKeeper_Concurrency_Test {
    private final Object mutex = new Object();
    private boolean excepted = false;
    private volatile boolean flag = false;

    @Test
    public void outsideMutex() throws InterruptedException {
        var keeper = new DumbConcurrentPlentyKeeper<Boolean>(5, mutex);

        var locker = new Thread(this::updateFlag);
        locker.start();

        while (!flag) {
            Thread.onSpinWait();
        }

        keeper.add(excepted);

        locker.join();
        assertTrue(keeper.get(0));
    }

    void updateFlag() {
        synchronized (mutex) {
            flag = true;
            excepted = true;
        }
    }
}
