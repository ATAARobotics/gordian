package edu.gordian.values;

public final class StaticValue implements Value {

    private final Object o;

    public StaticValue(Object o) {
        if(o == null) {
            throw new NullPointerException("Value given was null");
        }
        this.o = o;
    }

    public Object getValue() {
        return o;
    }

    public String toString() {
        return o.toString();
    }
}
