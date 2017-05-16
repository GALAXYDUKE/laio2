package com.bofsoft.laio.common;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerUtil {
    private static final AtomicInteger mAtomicInteger = new AtomicInteger();

    public static int getIncrementID() {
        return mAtomicInteger.getAndIncrement(); // 获取当前的值并自增
    }

}
