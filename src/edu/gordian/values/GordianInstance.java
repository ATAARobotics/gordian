package edu.gordian.values;

import edu.gordian.elements.GordianAnalyser;
import edu.gordian.elements.GordianInterpreter;
import edu.gordian.internal.GordianMethods;
import edu.gordian.internal.GordianStorage;
import edu.gordian.scopes.GordianRuntime;
import language.element.Analyser;
import language.internal.Methods;
import language.internal.Storage;
import language.scope.Instance;
import language.scope.Scope;
import language.value.Interpreter;

public class GordianInstance implements Instance {

    private final Scope scope;
    private final Methods methods;
    private final Storage storage;
    private final Analyser analyser = new GordianAnalyser(this);
    private final Interpreter interpreter = new GordianInterpreter(this);

    public GordianInstance(Scope scope, Instance inheret, String internals) {
        this.scope = scope;
        this.methods = new GordianMethods(inheret.methods());
        this.storage = new GordianStorage(inheret.storage());

        run(internals);
    }

    public GordianInstance(Scope scope, String internals) {
        this.scope = scope;
        this.methods = new GordianMethods();
        this.storage = new GordianStorage();

        run(internals);
    }

    public Scope parent() {
        return scope;
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
