package edu.gordian.values;

import language.scope.Instance;
import language.scope.Scope;

public class GordianClassFactory {

    private Scope scope = null;
    private Instance inheret = null;
    private String internals = "";

    public GordianClassFactory setScope(Scope scope) {
        this.scope = scope;
        return this;
    }

    public GordianClassFactory setInheret(Instance inheret) {
        this.inheret = inheret;
        return this;
    }

    public GordianClassFactory setInternals(String internals) {
        this.internals = internals;
        return this;
    }

    public GordianClass toClass() {
        return new GordianClass(scope, inheret, internals);
    }
}
