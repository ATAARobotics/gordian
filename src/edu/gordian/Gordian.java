package edu.gordian;

import language.scope.Scope;
import edu.gordian.scopes.GordianRuntime;

public final class Gordian {

    private final Scope runtime;

    public Gordian() {
        runtime = new GordianRuntime();
    }

    public void addMethod(Method m) {
        runtime.methods().put(m.getName(), m);
    }

    public void addMethods(Method[] m) {
        for (int x = 0; x < m.length; x++) {
            addMethod(m[x]);
        }
    }

    public void addVariable(Variable m) {
        runtime.storage().set(m.getName(), m.getValue());
    }

    public void addVariable(Variable[] m) {
        for (int x = 0; x < m.length; x++) {
            addVariable(m[x]);
        }
    }

    public Scope getRuntime() {
        return runtime;
    }

    public void run(String s) {
        runtime.run(s);
    }
}
