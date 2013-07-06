package edu.gordian.elements.methods;

import edu.gordian.Strings;

public abstract class UserMethod implements MethodBase {

    private final String name;

    public UserMethod(String name) {
        if (name == null || Strings.isEmpty(name)) {
            throw new IllegalArgumentException("Name given was invalid");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
