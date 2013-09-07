package edu.gordian.scopes;

import edu.gordian.Method;
import edu.gordian.Variable;
import edu.gordian.elements.GordianAnalyser;
import edu.gordian.elements.GordianInterpreter;
import edu.gordian.internal.GordianMethods;
import edu.gordian.internal.GordianStorage;
import language.element.Analyser;
import language.internal.Methods;
import language.internal.Storage;
import language.scope.Instance;
import language.scope.Scope;
import language.value.Interpreter;

public class EmptyClass implements Instance {

    private final Methods methods = new GordianMethods();
    private final Storage storage = new GordianStorage();
    private final Analyser analyser = new GordianAnalyser(this);
    private final Interpreter interpreter = new GordianInterpreter(this);

    public EmptyClass() {
    }

    public EmptyClass(Method[] methods, Variable[] variables) {
        for (int x = 0; x < methods.length; x++) {
            this.methods.put(methods[x].getName(), methods[x]);
        }
        for (int x = 0; x < variables.length; x++) {
            this.storage.put(variables[x].getName(), variables[x].getValue());
        }
    }

    public final Scope parent() {
        return null;
    }

    public final Methods methods() {
        return methods;
    }

    public final Storage storage() {
        return storage;
    }

    public void run(String i) {
    }

    public final Analyser getAnalyser() {
        return analyser;

    }

    public final Interpreter getInterpreter() {
        return interpreter;
    }
}
