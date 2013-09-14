package edu.gordian.values;

import edu.gordian.elements.GordianAnalyser;
import edu.gordian.elements.GordianInterpreter;
import edu.gordian.scopes.GordianRuntime;
import language.element.Analyser;
import language.internal.Methods;
import language.internal.Storage;
import language.scope.Instance;
import language.scope.Scope;
import language.value.Interpreter;

public class GordianInstance implements Instance {

    private final Scope scope;
    private final Instance parent;
    private final Methods methods;
    private final Storage storage;
    private final Analyser analyser = new GordianAnalyser(this);
    private final Interpreter interpreter = new GordianInterpreter(this);

    public GordianInstance(Scope scope, Instance parent, Methods methods, Storage storage) {
        this.scope = scope;
        this.parent = parent;
        this.methods = methods;
        this.storage = storage;
        
        storage.put("parent", parent);
    }

    public Scope container() {
        return scope;
    }

    public Instance parent() {
        return parent;
    }

    public Methods methods() {
        return methods;
    }

    public Storage storage() {
        return storage;
    }

    public void run(String i) {
        GordianRuntime.run(this, i);
    }

    public Analyser getAnalyser() {
        return analyser;
    }

    public Interpreter getInterpreter() {
        return interpreter;
    }
}
