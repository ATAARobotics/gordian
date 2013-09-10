package edu.gordian.scopes;

import edu.gordian.elements.GordianAnalyser;
import edu.gordian.elements.GordianInterpreter;
import language.element.Analyser;
import edu.gordian.internal.GordianMethods;
import edu.gordian.internal.GordianStorage;
import language.internal.Methods;
import language.internal.Storage;
import language.scope.Scope;
import language.value.Interpreter;

public class GordianScope implements Scope {

    private final Scope scope;
    private final Methods methods;
    private final Storage storage;
    private final Analyser analyser = new GordianAnalyser(this);
    private final Interpreter interpreter = new GordianInterpreter(this);

    public static void run(Scope scope, String s) {
        new GordianScope(scope).run(s.substring(s.indexOf(";") + 1));
    }

    public GordianScope(Scope scope) {
        this.scope = scope;
        this.methods = new GordianMethods(scope.methods());
        this.storage = new GordianStorage(scope.storage());
    }

    public Scope container() {
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
