package edu.gordian.scopes;

import com.sun.squawk.util.StringTokenizer;
import edu.gordian.Strings;
import edu.gordian.elements.declarations.Declaration;
import edu.gordian.elements.declarations.ValueAdjustment;
import edu.gordian.elements.methods.Method;
import edu.gordian.elements.methods.MethodBase;
import edu.gordian.elements.methods.ReturningMethod;
import edu.gordian.elements.methods.UserMethod;
import edu.gordian.values.GordianNumber;
import edu.gordian.values.ReturningMethodBase;
import edu.gordian.values.StaticValue;
import edu.gordian.values.UserReturningMethod;
import edu.gordian.values.Value;
import edu.gordian.values.calculations.Addition;
import edu.gordian.values.calculations.Division;
import edu.gordian.values.calculations.Modulus;
import edu.gordian.values.calculations.Multiplication;
import edu.gordian.values.calculations.Subtraction;
import edu.gordian.values.comparisons.Equals;
import edu.gordian.values.comparisons.NotEquals;
import edu.gordian.values.comparisons.numbers.Greater;
import edu.gordian.values.comparisons.numbers.GreaterOrEqual;
import edu.gordian.values.comparisons.numbers.Less;
import edu.gordian.values.comparisons.numbers.LessOrEqual;
import edu.wpi.first.wpilibj.networktables2.util.List;
import java.util.Enumeration;
import java.util.Hashtable;

/**
 * The representation of a scope in the context of Gordian. A scope can be
 * anything from the entire program to a 14-times nested if statement.
 *
 * @author Joel Gallant
 */
public class Scope {

    private final MapGroup publicVars;
    private final MapGroup publicMethods;
    private final MapGroup publicReturning;
    private final Hashtable privateVars = new Hashtable();
    private final Hashtable privateMethods = new Hashtable();
    private final Hashtable privateReturning = new Hashtable();
    private final Scope[] parents;

    /**
     * Constructs an empty scope. Empty scopes have nothing predefined. This
     * should be used for entire scripts.
     */
    public Scope() {
        publicVars = new MapGroup(new Hashtable());
        publicMethods = new MapGroup(new Hashtable());
        publicReturning = new MapGroup(new Hashtable());
        parents = new Scope[0];
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
        publicMethods = new MapGroup(new Hashtable());
        for (int x = 0; x < methods.length; x++) {
            publicMethods.put(methods[x].getName(), methods[x]);
        }
        publicReturning = new MapGroup(new Hashtable());
        for (int x = 0; x < returning.length; x++) {
            publicReturning.put(returning[x].getName(), returning[x]);
        }

        publicVars = new MapGroup(new Hashtable());
        parents = new Scope[0];
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
        parents = new Scope[scope.parents.length + 1];
        System.arraycopy(scope.parents, 0, parents, 0, scope.parents.length);
        parents[parents.length - 1] = scope;

        publicVars = new MapGroup(scope.publicVars);
        publicMethods = new MapGroup(scope.publicMethods);
        publicReturning = new MapGroup(scope.publicReturning);

        publicVars.add(scope.privateVars);
        publicMethods.add(scope.privateMethods);
        publicReturning.add(scope.privateReturning);
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
        if (Strings.contains(e, "\"")) {
            return new StaticValue(Strings.replaceAll(e, "\"", ""));
        }
        try {
            return new StaticValue(GordianNumber.valueOf(e));
        } catch (NumberFormatException ex) {
        }
        if (e.equalsIgnoreCase("true")) {
            return new StaticValue(Boolean.TRUE);
        }
        if (e.equalsIgnoreCase("false")) {
            return new StaticValue(Boolean.FALSE);
        }

        if (e.startsWith("!")) {
            return new StaticValue(Boolean.valueOf(!getBoolean(e.substring(1))));
        }
        if (Strings.contains(e, "||")) {
            return new StaticValue(Boolean.valueOf(getBoolean(e.substring(0, e.indexOf("||")))
                    || getBoolean(e.substring(e.indexOf("||") + 2))));
        }
        if (Strings.contains(e, "&&")) {
            return new StaticValue(Boolean.valueOf(getBoolean(e.substring(0, e.indexOf("&&")))
                    && getBoolean(e.substring(e.indexOf("&&") + 2))));
        }
        if (Strings.contains(e, "==")) {
            return new Equals(toValue(Strings.before(e, "==")), toValue(Strings.after(e, "==")));
        }
        if (Strings.contains(e, "!=")) {
            return new NotEquals(toValue(Strings.before(e, "!=")), toValue(Strings.after(e, "!=")));
        }
        if (Strings.contains(e, ">=")) {
            return new GreaterOrEqual(toValue(Strings.before(e, ">=")), toValue(Strings.after(e, ">=")));
        }
        if (Strings.contains(e, "<=")) {
            return new LessOrEqual(toValue(Strings.before(e, "<=")), toValue(Strings.after(e, "<=")));
        }
        if (Strings.contains(e, ">")) {
            return new Greater(toValue(Strings.before(e, '>')), toValue(Strings.after(e, '>')));
        }
        if (Strings.contains(e, "<")) {
            return new Less(toValue(Strings.before(e, '<')), toValue(Strings.after(e, '<')));
        }

        if (Strings.contains(e, '(') && Strings.contains(e, ')')) {
            // METHOD
            String name = e.substring(0, e.indexOf('('));
            String[] args = getArgs(e.substring(e.indexOf('(') + 1, e.lastIndexOf(')')));
            Value[] a = toValues(args);
            if (publicReturning.containsKey(name)) {
                return new StaticValue(((ReturningMethodBase) publicReturning.get(name)).runFor(a));
            } else if (privateReturning.containsKey(name)) {
                return new StaticValue(((ReturningMethodBase) privateReturning.get(name)).runFor(a));
            }
        }

        if (privateVars.containsKey(e)) {
            return (Value) privateVars.get(e);
        }
        if (publicVars.containsKey(e)) {
            return (Value) publicVars.get(e);
        }
        if (Strings.contains(e, '=')
                && e.indexOf('=') != e.indexOf("!=")
                && e.indexOf('=') != e.indexOf("==")
                && e.indexOf('=') != e.indexOf("<=")
                && e.indexOf('=') != e.indexOf(">=")) {
            return new Declaration(this, e.substring(0, e.indexOf('=')), e.substring(e.indexOf('=') + 1));
        }
        if (Strings.containsThatIsnt(e, '-', "--")) {
            return new Subtraction(GordianNumber.valueOf(getNumber(e.substring(0, Strings.indexThatIsnt(e, '-', "--")))),
                    GordianNumber.valueOf(getNumber(e.substring(Strings.indexThatIsnt(e, '-', "--") + 1))));
        }
        if (Strings.containsThatIsnt(e, '+', "++")
                // Need to ensure this is math (could be string concatenation)
                && toValue(e.substring(0, Strings.indexThatIsnt(e, '+', "++"))).getValue() instanceof GordianNumber
                && toValue(e.substring(Strings.indexThatIsnt(e, '+', "++") + 1)).getValue() instanceof GordianNumber) {
            return new Addition(GordianNumber.valueOf(getNumber(e.substring(0, Strings.indexThatIsnt(e, '+', "++")))),
                    GordianNumber.valueOf(getNumber(e.substring(Strings.indexThatIsnt(e, '+', "++") + 1))));
        }
        if (Strings.contains(e, '/')) {
            return new Division(GordianNumber.valueOf(getNumber(e.substring(0, e.indexOf('/')))),
                    GordianNumber.valueOf(getNumber(e.substring(e.indexOf('/') + 1))));
        }
        if (Strings.contains(e, '*')) {
            return new Multiplication(GordianNumber.valueOf(getNumber(e.substring(0, e.indexOf('*')))),
                    GordianNumber.valueOf(getNumber(e.substring(e.indexOf('*') + 1))));
        }
        if (Strings.contains(e, '%')) {
            return new Modulus(GordianNumber.valueOf(getNumber(e.substring(0, e.indexOf('%')))),
                    GordianNumber.valueOf(getNumber(e.substring(e.indexOf('%') + 1))));
        }

        if (e.endsWith("++")) {
            return new ValueAdjustment(this, e.substring(0, e.length() - 2), +1);
        }
        if (e.endsWith("--")) {
            return new ValueAdjustment(this, e.substring(0, e.length() - 2), -1);
        }
        if (Strings.containsThatIsnt(e, '+', "++")
                // STRING CONCATENATION
                && (!(toValue(e.substring(0, Strings.indexThatIsnt(e, '+', "++"))).getValue() instanceof GordianNumber)
                || !(toValue(e.substring(Strings.indexThatIsnt(e, '+', "++") + 1)).getValue() instanceof GordianNumber))) {
            return new StaticValue(toValue(e.substring(0, Strings.indexThatIsnt(e, '+', "++"))).getValue().toString()
                    + toValue(e.substring(Strings.indexThatIsnt(e, '+', "++") + 1)).getValue().toString());
        }

        return new StaticValue(e);
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

        DefinedMethod m = null;
        if (this instanceof DefinedMethod) {
            m = (DefinedMethod) this;
        } else {
            for (int x = parents.length - 1; x >= 0; x--) {
                if (parents[x] instanceof DefinedMethod) {
                    m = (DefinedMethod) parents[x];
                    break;
                }
            }
        }

        if (e.startsWith("return ") && m != null) {
            return new Return(m, e.substring(e.indexOf("return ") + 7));
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
            if (privateMethods.containsKey(name)) {
                return new Method((MethodBase) privateMethods.get(name), a);
            } else if (publicMethods.containsKey(name)) {
                return new Method((MethodBase) publicMethods.get(name), a);
            } else if (privateReturning.containsKey(name)) {
                return new ReturningMethod((ReturningMethodBase) privateReturning.get(name), a);
            } else if (publicReturning.containsKey(name)) {
                return new ReturningMethod((ReturningMethodBase) publicReturning.get(name), a);
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
        return privateVars.containsKey(key) || publicVars.containsKey(key);
    }

    /**
     * Returns the value of the variable associated with the key.
     *
     * @param key string used to access variable
     * @return key as a variable
     */
    public final Value getVariable(String key) {
        if (privateVars.containsKey(key)) {
            return (Value) privateVars.get(key);
        } else if (publicVars.containsKey(key)) {
            return (Value) publicVars.get(key);
        } else {
            throw new NullPointerException("Variable " + key + " was not found in scope");
        }
    }

    /**
     * Returns if the value is a public variable.
     *
     * @param key string used to access variable
     * @return if key is a variable
     */
    protected final boolean isPublicVariable(String key) {
        return publicVars.containsKey(key);
    }

    /**
     * Returns the public value of the variable associated with the key.
     *
     * @param key string used to access variable
     * @return key as a variable
     */
    protected final Value getPublicVariable(String key) {
        if (publicVars.containsKey(key)) {
            return (Value) publicVars.get(key);
        } else {
            throw new NullPointerException("Variable " + key + " was not found in scope");
        }
    }

    /**
     * Returns if the value is a private variable.
     *
     * @param key string used to access variable
     * @return if key is a variable
     */
    protected final boolean isPrivateVariable(String key) {
        return privateVars.containsKey(key);
    }

    /**
     * Returns the private value of the variable associated with the key.
     *
     * @param key string used to access variable
     * @return key as a variable
     */
    protected final Value getPrivateVariable(String key) {
        if (privateVars.containsKey(key)) {
            return (Value) privateVars.get(key);
        } else {
            throw new NullPointerException("Variable " + key + " was not found in scope");
        }
    }

    /**
     * Returns whether it's valid to save a variable as the key.
     *
     * @param key string used to access variable
     * @return if program can save a variable under the key
     */
    public static boolean isValidKey(String key) {
        try {
            Double.parseDouble(key);
            return false;
        } catch (NumberFormatException ex) {
            return !(Strings.isEmpty(key) || Strings.contains(key, "\"")
                    || key.equalsIgnoreCase("true") || key.equalsIgnoreCase("false")
                    || key.startsWith("!") || Strings.contains(key, "||") || Strings.contains(key, "&&")
                    || Strings.contains(key, '=') || Strings.contains(key, '>') || Strings.contains(key, '<')
                    || Strings.contains(key, '(') || Strings.contains(key, ')') || Strings.contains(key, '+')
                    || Strings.contains(key, '-') || Strings.contains(key, '*') || Strings.contains(key, '/'));
        }
    }

    /**
     * Sets the variable associated with the key to a value.
     *
     * @param key string used to access variable
     * @param value value to associate with key
     */
    public final void setVariable(String key, Value value) {
        if (publicVars.containsKey(key)) {
            setPublicVariable(key, value);
        } else {
            setPrivateVariable(key, value);
        }
    }

    /**
     * Sets the public variable associated with the key to a value.
     *
     * @param key string used to access variable
     * @param value value to associate with key
     */
    public final void setPublicVariable(String key, Value value) {
        if (isValidKey(key)) {
            publicVars.put(key, value);
        } else {
            throw new RuntimeException(key + " is not a valid key for a variable");
        }
    }

    /**
     * Sets the private variable associated with the key to a value.
     *
     * @param key string used to access variable
     * @param value value to associate with key
     */
    public final void setPrivateVariable(String key, Value value) {
        if (isValidKey(key)) {
            privateVars.put(key, value);
        } else {
            throw new RuntimeException(key + " is not a valid key for a variable");
        }
    }

    /**
     * Adds a method to be able to be used.
     *
     * @param name name of the method to call
     * @param base method to call
     */
    public final void addMethod(String name, MethodBase base) {
        if (privateMethods.containsKey(name) || Strings.contains(name, '=')) {
            throw new RuntimeException("Cannot create another " + name + " method");
        }
        privateMethods.put(name, base);
    }

    /**
     * Adds a method to be able to be used anywhere.
     *
     * @param name name of the method to call
     * @param base method to call
     */
    public final void addGlobalMethod(String name, MethodBase base) {
        if (publicMethods.containsKey(name) || Strings.contains(name, '=')) {
            throw new RuntimeException("Cannot create another " + name + " method");
        }
        publicMethods.put(name, base);
    }

    /**
     * Adds a returning method to be able to be used.
     *
     * @param name name of the method to call (abides by variable name
     * limitations)
     * @param base method to call
     */
    public final void addReturning(String name, ReturningMethodBase base) {
        if (privateReturning.containsKey(name) || !isValidKey(name)) {
            throw new RuntimeException("Cannot create another " + name + " method");
        }
        privateReturning.put(name, base);
    }

    /**
     * Adds a returning method to be able to be used anywhere.
     *
     * @param name name of the method to call (abides by variable name
     * limitations)
     * @param base method to call
     */
    public final void addGlobalReturning(String name, ReturningMethodBase base) {
        if (publicReturning.containsKey(name) || !isValidKey(name)) {
            throw new RuntimeException("Cannot create another " + name + " method");
        }
        publicReturning.put(name, base);
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
            List l = new List();
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

    private double getNumber(String val) {
        Object v = toValue(val).getValue();
        if (v instanceof GordianNumber) {
            return ((GordianNumber) v).doubleValue();
        } else if (v instanceof Double) {
            return ((Double) v).doubleValue();
        } else {
            throw new ClassCastException(val + " is not a number");
        }
    }

    private boolean getBoolean(String val) {
        return ((Boolean) toValue(val).getValue()).booleanValue();
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
            privateVars.remove(key);
            publicVars.remove(key);
        }
    }

    private final class Make implements Runnable {

        private final String key;

        public Make(String key) {
            this.key = key;
        }

        public void run() {
            setVariable(key, new StaticValue(""));
        }
    }

    private static final class MapGroup {

        private final List maps = new List();

        public MapGroup(Hashtable start) {
            maps.add(start);
        }

        public MapGroup(MapGroup group) {
            for (int x = 0; x < group.maps.size(); x++) {
                maps.add(group.maps.get(x));
            }
        }

        public void add(Hashtable h) {
            maps.add(h);
        }

        public Enumeration keys() {
            return new Enumeration() {
                private final Enumeration[] enumerations;
                private int i;

                {
                    enumerations = new Enumeration[maps.size()];
                    for (int x = 0; x < maps.size(); x++) {
                        enumerations[x] = ((Hashtable) maps.get(x)).keys();
                    }
                }

                public boolean hasMoreElements() {
                    return enumerations[i].hasMoreElements()
                            || (++i < enumerations.length && enumerations[i].hasMoreElements());
                }

                public Object nextElement() {
                    // needed to increment i
                    return hasMoreElements() ? enumerations[i].nextElement() : null;
                }
            };
        }

        public Enumeration Elements() {
            return new Enumeration() {
                private final Enumeration[] enumerations;
                private int i;

                {
                    enumerations = new Enumeration[maps.size()];
                    for (int x = 0; x < maps.size(); x++) {
                        enumerations[x] = ((Hashtable) maps.get(x)).elements();
                    }
                }

                public boolean hasMoreElements() {
                    return enumerations[i].hasMoreElements()
                            || (++i < enumerations.length && enumerations[i].hasMoreElements());
                }

                public Object nextElement() {
                    // needed to increment i
                    return hasMoreElements() ? enumerations[i].nextElement() : null;
                }
            };
        }

        public boolean contains(Object value) {
            for (int x = 0; x < maps.size(); x++) {
                if (((Hashtable) maps.get(x)).contains(value)) {
                    return true;
                }
            }
            return false;
        }

        public boolean containsKey(Object key) {
            for (int x = 0; x < maps.size(); x++) {
                if (((Hashtable) maps.get(x)).containsKey(key)) {
                    return true;
                }
            }
            return false;
        }

        public Object get(Object key) {
            for (int x = 0; x < maps.size(); x++) {
                if (((Hashtable) maps.get(x)).containsKey(key)) {
                    return ((Hashtable) maps.get(x)).get(key);
                }
            }
            return null;
        }

        public Object put(Object key, Object value) {
            for (int x = 0; x < maps.size(); x++) {
                if (((Hashtable) maps.get(x)).containsKey(key)) {
                    return ((Hashtable) maps.get(x)).put(key, value);
                }
            }
            return ((Hashtable) maps.get(maps.size() - 1)).put(key, value);
        }

        public Object remove(Object key) {
            // Nothing is ever removed - don't worry
            return null;
        }

        public void clear() {
        }

        public int size() {
            int s = 0;
            for (int x = 0; x < maps.size(); x++) {
                s += ((Hashtable) maps.get(x)).size();
            }
            return s;
        }

        public boolean isEmpty() {
            return size() == 0;
        }
    }
}