package com.test.server;

/**
 * Created by davidferreira on 12/08/16.
 */
public class Value {
    private int value;

    public Value() {
    }

    public Value(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Value value1 = (Value) o;

        if (value != value1.value) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
