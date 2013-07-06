package edu.gordian.values;

import edu.gordian.Strings;

public abstract class UserReturningMethod implements ReturningMethodBase {

    private final String name;

    public UserReturningMethod(String name) {
        if (name == null || Strings.isEmpty(name)) {
            throw new IllegalArgumentException("Name given was invalid");
        }
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
