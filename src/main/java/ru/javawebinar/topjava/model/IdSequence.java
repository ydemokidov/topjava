package ru.javawebinar.topjava.model;

import java.util.concurrent.atomic.AtomicLong;

public class IdSequence {
    private static final AtomicLong sequence = new AtomicLong(System.currentTimeMillis() / 1000);

    public static long getNext() {
        return sequence.incrementAndGet();
    }
}
