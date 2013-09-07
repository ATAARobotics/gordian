package edu.gordian.values;

import edu.gordian.elements.GordianAnalyser;
import edu.gordian.elements.GordianInterpreter;
import edu.gordian.internal.GordianMethods;
import edu.gordian.internal.GordianStorage;
import edu.gordian.scopes.GordianRuntime;
import language.element.Analyser;
import language.instruction.Method;
import language.internal.Methods;
import language.internal.Storage;
import language.scope.ClassGenerator;
import language.scope.Instance;
import language.scope.Scope;
import language.value.Interpreter;
import language.value.Value;

public final class GordianClass implements ClassGenerator {

    private final Scope scope;
    private final String internals;

    public GordianClass(Scope scope, String internals) {
        this.scope = scope;
        this.internals = internals;
    }

    public Instance construct() {
        return new GordianInstance(scope, internals);
    }

    private final static class GordianInstance implements Instance, Scope {

        private final Scope scope;
        private final Methods methods;
        private final Storage storage;
        private final Analyser analyser = new GordianAnalyser(this);
        private final Interpreter interpreter = new GordianInterpreter(this);

        public GordianInstance(Scope scope, String internals) {
            this.scope = scope;
            this.methods = new GordianMethods();
            this.storage = new GordianStorage();

            this.methods.clone(scope.methods());
            this.storage.clone(scope.storage());
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

        public Method getMethod(String name) {
            return methods.get(name);
        }

        public Value getValue(String name) {
            return storage.get(name);
        }
    }
}
