package edu.gordian.elements.methods;

public abstract class UserMethod implements MethodBase {

    private final String name;

    public UserMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
