package com.application.android.imageloader.util.cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruHashMap<K, V> extends LinkedHashMap<K, V> {

    private final int capacity;

    LruHashMap(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry entry) {
        return size() > capacity;
    }


}
