package edu.gordian.values;

public abstract class UserReturningMethod implements ReturningMethodBase {

    private final String name;

    public UserReturningMethod(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
