package edu.gordian.values;

public class StaticValue implements Value {

    private final Object o;

    public StaticValue(Object o) {
        this.o = o;
    }

    public Object getValue() {
        return o;
    }

    public String toString() {
        return o.toString();
    }
}
