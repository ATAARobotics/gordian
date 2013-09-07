package edu.gordian.values;

import edu.gordian.scopes.EmptyInstance;
import language.scope.ClassGenerator;
import language.scope.Instance;
import language.scope.Scope;

public final class GordianClass extends EmptyInstance implements ClassGenerator {

    private final Scope scope;
    private final String internals;

    public static void run(Scope scope, String s) {
        scope.storage().put(s.substring(5, s.indexOf(":")), new GordianClass(scope, s.substring(s.indexOf(";") + 1)));
    }

    public GordianClass(Scope scope, String internals) {
        this.scope = scope;
        this.internals = internals;
    }

    public Instance construct() {
        return new GordianInstance(scope, internals);
    }
}
