package edu.gordian.scopes;

import com.sun.squawk.util.StringTokenizer;
import edu.first.util.list.ArrayList;
import edu.first.util.list.List;
import edu.gordian.Strings;
import edu.gordian.elements.declarations.Declaration;
import edu.gordian.elements.declarations.ValueAdjustment;
import edu.gordian.elements.methods.Method;
import edu.gordian.elements.methods.MethodBase;
import edu.gordian.elements.methods.ReturningMethod;
import edu.gordian.elements.methods.UserMethod;
import edu.gordian.values.ReturningMethodBase;
import edu.gordian.values.UserReturningMethod;
import edu.gordian.values.Value;
import edu.gordian.values.Values;
import edu.gordian.values.adjustments.Negative;
import edu.gordian.values.adjustments.Positive;
import edu.gordian.values.calculations.Addition;
import edu.gordian.values.calculations.Division;
import edu.gordian.values.calculations.Modulus;
import edu.gordian.values.calculations.Multiplication;
import edu.gordian.values.calculations.Subtraction;
import edu.gordian.values.expressions.Equals;
import edu.gordian.values.expressions.NotEquals;
import edu.gordian.values.expressions.StringConcat;
import edu.gordian.values.expressions.numbers.Greater;
import edu.gordian.values.expressions.numbers.GreaterOrEqual;
import edu.gordian.values.expressions.numbers.Less;
import edu.gordian.values.expressions.numbers.LessOrEqual;

/**
 * The representation of a scope in the context of Gordian. A scope can be
 * anything from the entire program to a 14-times nested if statement.
 *
 * @author Joel Gallant
 */
public class Scope {

    private final ScopeStorage storage;
    private final DefinedMethod parent;

    /**
     * Constructs an empty scope. Empty scopes have nothing predefined. This
     * should be used for entire scripts.
     */
    public Scope() {
        storage = new ScopeStorage();
        parent = null;
    }

    /**
     * Constructs a general scope. This should be used for entire scripts.
     *
     * @param methods methods that the script will have access to
     * @param returning returning methods that the script will have access to
     */
    public Scope(UserMethod[] methods, UserReturningMethod[] returning) {
        if (methods == null) {
            throw new NullPointerException("Methods were null");
        }
        if (returning == null) {
            throw new NullPointerException("Returning methods were null");
        }

        storage = new ScopeStorage();

        for (int x = 0; x < methods.length; x++) {
            storage.getMethods().setPublicValue(methods[x].getName(), methods[x]);
        }
        for (int x = 0; x < returning.length; x++) {
            storage.getReturning().setPublicValue(returning[x].getName(), returning[x]);
        }

        parent = null;
    }

    /**
     * Constructs a scope that is nested inside of another scope. This scope
     * will inherit the elements of its parent.
     *
     * @param scope parent scope that created this one
     */
    public Scope(Scope scope) {
        if (scope == null) {
            throw new NullPointerException("Parent scope is null");
        }
        if (scope instanceof DefinedMethod) {
            parent = (DefinedMethod) scope;
        } else if (this instanceof DefinedMethod) {
            parent = (DefinedMethod) this;
        } else {
            parent = scope.parent;
        }

        storage = new ScopeStorage(scope.storage);
    }

    /**
     * Converts a string value into a {@link Value} in the context of this
     * scope.
     *
     * @param e value as it appears in code
     * @return value as interpreted by the code
     */
    public final Value toValue(String e) {
        if (e == null || Strings.isEmpty(e)) {
            throw new IllegalArgumentException("Value is not valid - " + e);
        }

        /* ADJUSTMENTS */
        if (Positive.is(this, e)) {
            return Positive.valueOf(this, e);
        } else if (Negative.is(this, e)) {
            return Negative.valueOf(this, e);

            /* USER DEFINED */
        } else if (storage.getVariables().isValue(e)) {
            return (Value) storage.getVariables().getValue(e);
        } else if (Strings.contains(e, '(') && Strings.contains(e, ')')
                && storage.getReturning().isValue(e.substring(0, e.indexOf('(')))) {
            int scope = 1;
            for (int x = e.indexOf('(') + 1; x < e.length(); x++) {
                if (e.charAt(x) == '(') {
                    scope++;
                } else if (e.charAt(x) == ')') {
                    scope--;
                }

                if (scope == 0) {
                    // X is last parentheses
                    if (Strings.isEmpty(e.substring(x + 1))) {
                        // Nothing after final bracket
                        String[] args = getArgs(e.substring(e.indexOf('(') + 1, x));
                        return Values.literal(((ReturningMethodBase) storage.getReturning().getValue(e.substring(0, e.indexOf('(')))).runFor(toValues(args)));
                    }
                }
            }

            /* EXPRESSIONS */
        } else if (GreaterOrEqual.is(e)) {
            return GreaterOrEqual.valueOf(this, e);
        } else if (LessOrEqual.is(e)) {
            return LessOrEqual.valueOf(this, e);
        } else if (Greater.is(e)) {
            return Greater.valueOf(this, e);
        } else if (Less.is(e)) {
            return Less.valueOf(this, e);
        } else if (Equals.is(e)) {
            return Equals.valueOf(this, e);
        } else if (NotEquals.is(e)) {
            return NotEquals.valueOf(this, e);
        } else if (StringConcat.is(this, e)) {
            return StringConcat.valueOf(this, e);

            /* CALCULATIONS */
        } else if (Subtraction.is(this, e)) {
            return Subtraction.valueOf(this, e);
        } else if (Addition.is(this, e)) {
            return Addition.valueOf(this, e);
        } else if (Division.is(this, e)) {
            return Division.valueOf(this, e);
        } else if (Multiplication.is(this, e)) {
            return Multiplication.valueOf(this, e);
        } else if (Modulus.is(this, e)) {
            return Modulus.valueOf(this, e);

            /* LITERALS */
        } else if (Values.isLiteralValue(e)) {
            return Values.literalValue(e);
        }

        throw new RuntimeException(e + " is not a value");
    }

    /**
     * Converts an array of strings into values using
     * {@link #toValue(java.lang.String)}.
     *
     * @param values original values
     * @return converted values
     */
    public final Value[] toValues(String[] values) {
        Value[] v = new Value[values.length];
        for (int x = 0; x < values.length; x++) {
            v[x] = toValue(values[x]);
        }
        return v;
    }

    /**
     * Converts a string into a runnable element that the program can run.
     *
     * @param e original string as it appears in code
     * @return executable element that the program can run
     * @throws Exception when string is not an instruction
     */
    public final Runnable toElement(String e) throws Exception {
        if (e == null || Strings.isEmpty(e)) {
            throw new IllegalArgumentException("Element is not valid - " + e);
        }

        if (e.startsWith("return ") && parent != null) {
            return new Return(parent, e.substring(e.indexOf("return ") + 7));
        }

        if (e.startsWith("del ")) {
            return new Remove(e.substring(4));
        }

        if (e.startsWith("make ")) {
            return new Make(e.substring(5));
        }

        if (Strings.contains(e, '=')
                && e.indexOf('=') != e.indexOf("!=")
                && e.indexOf('=') != e.indexOf("==")
                && e.indexOf('=') != e.indexOf("<=")
                && e.indexOf('=') != e.indexOf(">=")
                && (e.indexOf('(') >= 0 ? (e.indexOf('=') < e.indexOf('(')) : true)
                && e.indexOf('=') > 0) {
            return new Declaration(this, e.substring(0, e.indexOf('=')), e.substring(e.indexOf('=') + 1));
        }
        if (Strings.contains(e, '(') && Strings.contains(e, ')')) {
            String name = e.substring(0, e.indexOf('('));
            String[] args = getArgs(e.substring(e.indexOf('(') + 1, e.lastIndexOf(')')));
            Value[] a = toValues(args);
            if (storage.getMethods().isValue(name)) {
                return new Method((MethodBase) storage.getMethods().getValue(name), a);
            } else if (storage.getReturning().isValue(name)) {
                return new ReturningMethod((ReturningMethodBase) storage.getReturning().getValue(name), a);
            }
        }
        if (Strings.contains(e, "++;")) {
            return new ValueAdjustment(this, e.substring(0, e.indexOf("++;")), +1);
        }
        if (Strings.contains(e, "--;")) {
            return new ValueAdjustment(this, e.substring(0, e.indexOf("--;")), -1);
        }

        throw new Exception("Not a valid instruction: " + e);
    }

    /**
     * Returns whether the scope contains the variable.
     *
     * @param key string used to access variable
     * @return if key is a variable
     */
    public final boolean isVariable(String key) {
        return storage.getVariables().isValue(key);
    }

    /**
     * Returns the value of the variable associated with the key.
     *
     * @param key string used to access variable
     * @return key as a variable
     */
    public final Value getVariable(String key) {
        return (Value) storage.getVariables().getValue(key);
    }

    /**
     * Returns if the value is a public variable.
     *
     * @param key string used to access variable
     * @return if key is a variable
     */
    protected final boolean isPublicVariable(String key) {
        return storage.getVariables().isPrivateValue(key);
    }

    /**
     * Returns the public value of the variable associated with the key.
     *
     * @param key string used to access variable
     * @return key as a variable
     */
    protected final Value getPublicVariable(String key) {
        return (Value) storage.getVariables().getPublicValue(key);
    }

    /**
     * Returns if the value is a private variable.
     *
     * @param key string used to access variable
     * @return if key is a variable
     */
    protected final boolean isPrivateVariable(String key) {
        return storage.getVariables().isPrivateValue(key);
    }

    /**
     * Returns the private value of the variable associated with the key.
     *
     * @param key string used to access variable
     * @return key as a variable
     */
    protected final Value getPrivateVariable(String key) {
        return (Value) storage.getVariables().getPrivateValue(key);
    }

    /**
     * Sets the variable associated with the key to a value.
     *
     * @param key string used to access variable
     * @param value value to associate with key
     */
    public final void setVariable(String key, Value value) {
        storage.getVariables().setValue(key, value);
    }

    /**
     * Sets the public variable associated with the key to a value.
     *
     * @param key string used to access variable
     * @param value value to associate with key
     */
    public final void setPublicVariable(String key, Value value) {
        storage.getVariables().setPublicValue(key, value);
    }

    /**
     * Sets the private variable associated with the key to a value.
     *
     * @param key string used to access variable
     * @param value value to associate with key
     */
    public final void setPrivateVariable(String key, Value value) {
        storage.getVariables().setPrivateValue(key, value);
    }

    /**
     * Adds a method to be able to be used.
     *
     * @param name name of the method to call
     * @param base method to call
     */
    public final void addMethod(String name, MethodBase base) {
        storage.getMethods().setValue(name, base);
    }

    /**
     * Adds a method to be able to be used anywhere.
     *
     * @param name name of the method to call
     * @param base method to call
     */
    public final void addGlobalMethod(String name, MethodBase base) {
        storage.getMethods().setPublicValue(name, base);
    }

    /**
     * Adds a returning method to be able to be used.
     *
     * @param name name of the method to call (abides by variable name
     * limitations)
     * @param base method to call
     */
    public final void addReturning(String name, ReturningMethodBase base) {
        storage.getReturning().setValue(name, base);
    }

    /**
     * Adds a returning method to be able to be used anywhere.
     *
     * @param name name of the method to call (abides by variable name
     * limitations)
     * @param base method to call
     */
    public final void addGlobalReturning(String name, ReturningMethodBase base) {
        storage.getReturning().setPublicValue(name, base);
    }

    /**
     * Converts a script into a {@link StringTokenizer} that can be run through.
     * Does all of the pre-run conversions that gets rid of unnecessary
     * information.
     *
     * @param script full script to split up
     * @return split up cleaned script
     */
    public static final StringTokenizer preRun(String script) {
        script = Strings.replaceAll(script + ';', '\n', ';');
        if (Strings.contains(script, ' ')) {
            script = removeSpaces(script, 0);
        }
        while (Strings.contains(script, '#')) {
            String toRemove = script.substring(script.indexOf('#'), (script.substring(script.indexOf('#'))).indexOf(';') + script.indexOf('#'));
            script = Strings.replace(script, toRemove, "");
        }
        return new StringTokenizer(script, ";");
    }

    /**
     * Runs the script.
     *
     * @param script full script to run
     * @throws Exception when script running encounters any kind of error
     */
    public void run(String script) throws Exception {
        StringTokenizer t = preRun(script);
        int line = 0;
        int scopes = 0;
        String scope = "";
        while (t.hasMoreElements()) {
            String next = t.nextToken();
            if (Strings.isEmpty(next)) {
                continue;
            }
            line++;

            if (next.startsWith("while") || next.startsWith("for")
                    || next.startsWith("if") || next.startsWith("def")) {
                scopes++;
            }
            if (next.toLowerCase().equals("end")) {
                scopes--;
            }
            if (scopes != 0) {
                scope += next + ';';
                continue;
            }

            try {
                if (scopes == 0 && scope.startsWith("while")) {
                    String start = scope.substring(0, scope.indexOf(';'));
                    String s = scope.substring(scope.indexOf(';') + 1);

                    // Validity check
                    if (!Strings.contains(start, '(') || !Strings.contains(start, ')')) {
                        throw new IllegalArgumentException("Argument was not given");
                    }

                    new While(start.substring(start.indexOf('(') + 1, start.lastIndexOf(')')), this).run(s);
                    scope = "";
                    continue;
                }
                if (scopes == 0 && scope.startsWith("if")) {
                    new If(this).run(scope);
                    scope = "";
                    continue;
                }
                if (scopes == 0 && scope.startsWith("for")) {
                    String start = scope.substring(0, scope.indexOf(';'));
                    String s = scope.substring(scope.indexOf(';') + 1);

                    // Validity check
                    if (!Strings.contains(start, '(') || !Strings.contains(start, ')')) {
                        throw new IllegalArgumentException("Argument was not given");
                    }

                    new For(start.substring(start.indexOf('(') + 1, start.lastIndexOf(')')), this).run(s);
                    scope = "";
                    continue;
                }
                if (scopes == 0 && scope.startsWith("def")) {
                    String name = scope.substring(scope.indexOf("def") + 3, scope.indexOf('('));
                    String start = scope.substring(0, scope.indexOf(';'));
                    String s = scope.substring(scope.indexOf(';') + 1);

                    // Validity check
                    if (!Strings.contains(start, '(') || !Strings.contains(start, ')')) {
                        throw new IllegalArgumentException("Arguments were not given");
                    }

                    DefinedMethod method = new DefinedMethod(Strings.split(start.substring(start.indexOf('(') + 1, start.lastIndexOf(')')), ','), s, this);
                    addMethod(name, method);
                    if (Strings.contains(s, "return ")) {
                        addReturning(name, method);
                    }
                    scope = "";
                    continue;
                }

                toElement(next).run();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new Exception("LINE " + line + " in " + getClass().getName() + " - " + ex.getClass().getName() + ": " + ex.getMessage());
            }
        }

        if (scopes != 0) {
            throw new RuntimeException("Scope was never completed. Use 'end' or 'fi' to complete scopes.");
        }
    }

    private String[] getArgs(String s) {
        if (!Strings.contains(s, '\"') && !Strings.contains(s, '(')) {
            return Strings.split(s, ',');
        } else {
            List l = new ArrayList();
            boolean inQuotes = false;
            int paren = 0;
            int last = 0;
            char[] d = s.toCharArray();
            for (int x = 0; x < d.length; x++) {
                if (d[x] == '\"') {
                    inQuotes = !inQuotes;
                } else if (d[x] == '(') {
                    paren++;
                } else if (d[x] == ')') {
                    paren--;
                }
                if (d[x] == ',' && !inQuotes && paren == 0 && !Strings.isEmpty(s.substring(last, x))) {
                    l.add(s.substring(last, x));
                    last = x + 1;
                }
                if (x == d.length - 1 && !Strings.isEmpty(s.substring(last))) {
                    l.add(s.substring(last));
                }
            }
            String[] args = new String[l.size()];
            for (int x = 0; x < args.length; x++) {
                args[x] = (String) l.get(x);
            }
            return args;
        }
    }

    private static String removeSpaces(final String s, int x) {
        String a = Strings.replaceAll(s, '\t', ' ');

        boolean inQuotes = false;
        x += a.substring(x).indexOf(' ');
        for (int i = 0; i < x; i++) {
            if (a.charAt(i) == '"') {
                inQuotes = !inQuotes;
            } else if (a.charAt(i) == ';') {
                inQuotes = false;
            }
        }
        if (!inQuotes
                // Special statements
                && !a.substring(x - 6 < 0 ? 0 : x - 6, x).equals("return")
                && !a.substring(x - 3 < 0 ? 0 : x - 3, x).equals("del")
                && !a.substring(x - 4 < 0 ? 0 : x - 4, x).equals("make")) {
            a = a.substring(0, x) + s.substring(x + 1);
        } else {
            if (a.substring(x + 1).indexOf(' ') == -1) {
                x = a.length() - 1;
            } else {
                x = a.substring(x + 1).indexOf(' ') + x + 1;
            }
        }

        if (Strings.contains(a.substring(x), ' ')) {
            return removeSpaces(a, x);
        }

        return a;
    }

    private final class Return implements Runnable {

        private final DefinedMethod definedMethod;
        private final String value;

        public Return(DefinedMethod definedMethod, String value) {
            this.definedMethod = definedMethod;
            this.value = value;
        }

        public void run() {
            definedMethod.returnValue(toValue(value).getValue());
        }
    }

    private final class Remove implements Runnable {

        private final String key;

        public Remove(String key) {
            this.key = key;
        }

        public void run() {
            storage.getVariables().remove(key);
        }
    }

    private final class Make implements Runnable {

        private final String key;

        public Make(String key) {
            this.key = key;
        }

        public void run() {
            setVariable(key, Values.emptyValue());
        }
    }
}