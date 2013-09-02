package edu.gordian;

import language.value.Value;

public class Variable {

    private final String name;
    private final Value val;

    public Variable(String name, Value val) {
        this.name = name;
        this.val = val;
    }

    public String getName() {
        return name;
    }

    public Value getValue() {
        return val;
    }
}
