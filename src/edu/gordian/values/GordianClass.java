package edu.gordian.values;

import edu.first.util.Strings;
import edu.gordian.scopes.EmptyInstance;
import language.scope.ClassGenerator;
import language.scope.Instance;
import language.scope.Scope;

public final class GordianClass extends EmptyInstance implements ClassGenerator {

    private final Scope scope;
    private final Instance inheret;
    private final String internals;

    public static void run(Scope scope, String s) {
        if (Strings.contains(s.substring(0, s.indexOf(";")), "(")) {
            String inheret = s.substring(0, s.indexOf(";"));
            inheret = inheret.substring(inheret.indexOf("(") + 1, inheret.lastIndexOf(')'));
            if (Strings.isEmpty(inheret)) {
                throw new IllegalArgumentException("Class (" + s.substring(5, s.indexOf("(")) + ") inherets an empty class");
            }

            scope.storage().put(s.substring(5, s.indexOf("(")),
                    new GordianClass(scope,
                    ((ClassGenerator) scope.getInterpreter().interpretValue(inheret)).construct(),
                    s.substring(s.indexOf(";") + 1)));
        } else {
            scope.storage().put(s.substring(5, s.indexOf(":")), new GordianClass(scope, s.substring(s.indexOf(";") + 1)));
        }

    }

    public GordianClass(Scope scope, Instance inheret, String internals) {
        this.scope = scope;
        this.inheret = inheret;
        this.internals = internals;
    }

    public GordianClass(Scope scope, String internals) {
        this.scope = scope;
        this.inheret = null;
        this.internals = internals;
    }

    public Instance construct() {
        if (inheret != null) {
            return new GordianInstance(scope, inheret, internals);
        } else {
            return new GordianInstance(scope, internals);
        }
    }
}
