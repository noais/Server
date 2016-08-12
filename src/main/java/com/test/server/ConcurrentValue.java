package com.test.server;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by davidferreira on 12/08/16.
 */
public class ConcurrentValue {
    private ConcurrentHashMap<String, Value> map;

    public ConcurrentValue() {
        this.map = new ConcurrentHashMap<>();
        this.map.put("value", new Value(0));
    }

    public Value get() {
        return this.map.get("value");
    }

    public void set(final Value value) {
        this.map.put("value", value);
    }

}
