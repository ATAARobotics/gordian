package edu.gordian;

public abstract class Method implements language.instruction.Method {

    private final String name;

    public Method(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
