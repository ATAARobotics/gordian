package edu.gordian.internal;

import language.value.Value;

public class ValueReturned extends RuntimeException {

    public final Value value;

    public ValueReturned(Value value) {
        this.value = value;
    }
}
