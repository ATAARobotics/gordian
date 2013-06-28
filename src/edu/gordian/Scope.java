package edu.gordian;

import edu.gordian.elements.methods.Method;
import edu.gordian.elements.methods.MethodBase;
import edu.gordian.elements.methods.ReturningMethod;
import edu.gordian.elements.methods.UserMethod;
import edu.gordian.values.ReturningMethodBase;
import edu.gordian.values.StaticValue;
import edu.gordian.values.UserReturningMethod;
import edu.gordian.values.Value;
import java.util.Arrays;
import java.util.Hashtable;

public class Scope {

    private final Hashtable publicVars;
    private final Hashtable publicMethods;
    private final Hashtable publicReturning;
    private final Hashtable privateVars;
    private final Hashtable privateMethods;

    public Scope() {
        publicMethods = new Hashtable();
        publicReturning = new Hashtable(0);
        publicVars = new Hashtable();
        privateVars = new Hashtable();
        privateMethods = new Hashtable();
    }

    public Scope(UserMethod[] methods, UserReturningMethod[] returning) {
        publicMethods = new Hashtable(methods.length);
        for (int x = 0; x < methods.length; x++) {
            publicMethods.put(methods[x].getName(), methods[x]);
        }
        publicReturning = new Hashtable(returning.length);
        for (int x = 0; x < returning.length; x++) {
            publicReturning.put(returning[x].getName(), returning[x]);
        }

        publicVars = new Hashtable();
        privateVars = new Hashtable();
        privateMethods = new Hashtable();
    }

    Scope(Scope scope) {
        publicVars = scope.publicVars;
        publicMethods = scope.publicMethods;
        publicReturning = scope.publicReturning;
        privateVars = scope.privateVars;
        privateMethods = scope.privateMethods;
    }

    public Value toValue(String e) {
        if (privateVars.containsKey(e)) {
            return new StaticValue(privateVars.get(e));
        }
        if (publicVars.containsKey(e)) {
            return new StaticValue(publicVars.get(e));
        }
        if (Strings.contains(e, '(') && Strings.contains(e, ')')) {
            // METHOD
            String name = e.substring(0, e.indexOf('('));
            String[] args = Strings.split(e.substring(e.indexOf('(') + 1, e.lastIndexOf(')')), ',');
            Value[] a = toValues(args);
            if (publicReturning.containsKey(name)) {
                return new StaticValue(((ReturningMethodBase) publicReturning.get(name)).run(a));
            }
        }

        try {
            return new StaticValue(Integer.valueOf(e));
        } catch (NumberFormatException ex) {
        }
        try {
            return new StaticValue(Double.valueOf(e));
        } catch (NumberFormatException ex) {
        }
        if (e.toLowerCase().equals("true") || e.toLowerCase().equals("false")) {
            return new StaticValue(Boolean.valueOf(e));
        }

        return new StaticValue(e);
    }

    public Value[] toValues(String[] values) {
        Value[] v = new Value[values.length];
        for (int x = 0; x < values.length; x++) {
            v[x] = toValue(values[x]);
        }
        return v;
    }

    public Element toElement(String e) throws Exception {
        if (Strings.contains(e, '=') && !Strings.contains(e, "")) {
        }
        if (Strings.contains(e, '(') && Strings.contains(e, ')')) {
            // METHOD
            String name = e.substring(0, e.indexOf('('));
            String[] args = Strings.split(e.substring(e.indexOf('(') + 1, e.lastIndexOf(')')), ',');
            Value[] a = toValues(args);
            if (privateMethods.containsKey(name)) {
                return new Method((MethodBase) privateMethods.get(name), a);
            } else if (publicMethods.containsKey(name)) {
                return new Method((MethodBase) publicMethods.get(name), a);
            } else if (publicReturning.containsKey(name)) {
                return new ReturningMethod((ReturningMethodBase) publicReturning.get(name), a);
            }
        }

        throw new Exception("Not a valid instruction: " + e);
    }

    private String removeSpaces(final String s, int start) {
        String a = s;

        boolean inQuotes = false;
        int x = start + s.substring(start).indexOf(' ');
        for (int i = 0; i < x; i++) {
            if (s.charAt(i) == '"') {
                inQuotes = !inQuotes;
            }
        }
        if (!inQuotes) {
            a = s.substring(0, x) + s.substring(x + 1);
        }

        if (Strings.contains(s.substring(x + 1), ' ')) {
            return removeSpaces(a, x + 1);
        }

        return a;
    }

    public void run(String script) throws Exception {
        script = script + ';';
        script = Strings.replace(script, '\n', ';');
        if (Strings.contains(script, ' ')) {
            script = removeSpaces(script, 0);
        }
        while (Strings.contains(script, '#')) {
            String toRemove = script.substring(script.indexOf('#'), (script.substring(script.indexOf('#'))).indexOf(';') + script.indexOf('#'));
            script = Strings.replace(script, toRemove, "");
        }
        Tokenizer t = new Tokenizer(script);
        while (t.hasMoreElements()) {
            toElement(t.nextToken()).run();
        }
    }
}
