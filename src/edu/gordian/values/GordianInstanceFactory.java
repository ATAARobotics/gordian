package edu.gordian.values;

import edu.gordian.internal.GordianMethods;
import edu.gordian.internal.GordianStorage;
import language.internal.Methods;
import language.internal.Storage;
import language.scope.Instance;
import language.scope.Scope;

public class GordianInstanceFactory {

    private Scope scope = null;
    private Instance parent = null;
    private final Methods methods = new GordianMethods();
    private final Storage storage = new GordianStorage();

    public GordianInstanceFactory setScope(Scope scope) {
        this.scope = scope;
        return this;
    }

    public GordianInstanceFactory setParent(Instance parent) {
        this.parent = parent;
        cloneMethods(parent.methods());
        cloneStorage(parent.storage());
        return this;
    }

    public GordianInstanceFactory cloneMethods(Methods m) {
        methods.clone(m);
        return this;
    }

    public GordianInstanceFactory cloneStorage(Storage s) {
        storage.clone(s);
        return this;
    }

    public Instance toInstance() {
        return new GordianInstance(scope, parent, methods, storage);
    }

    public Instance toInstance(String internals) {
        GordianInstance i = new GordianInstance(scope, parent, methods, storage);
        i.run(internals);
        return i;
    }
}
