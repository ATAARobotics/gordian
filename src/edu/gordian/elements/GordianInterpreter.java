package edu.gordian.elements;

import edu.first.util.Strings;
import edu.first.util.list.ArrayList;
import edu.first.util.list.Iterator;
import edu.first.util.list.List;
import edu.gordian.instructions.GordianDeclaration;
import edu.gordian.scopes.GordianRuntime;
import edu.gordian.values.GordianBoolean;
import edu.gordian.values.GordianClass;
import edu.gordian.values.GordianNumber;
import edu.gordian.values.GordianString;
import language.instruction.Method;
import language.operator.Operator;
import language.scope.Scope;
import language.value.Interpreter;
import language.value.Value;

public class GordianInterpreter implements Interpreter {

    private final Scope scope;

    public GordianInterpreter(Scope scope) {
        this.scope = scope;
    }

    public Value interpretValue(String s) {
        if (Strings.isEmpty(s)) {
            return new GordianString(s);
        }

        // Literals
        GordianNumber n = GordianNumber.toNumber(s);
        if (n != null) {
            return n;
        }

        if (s.equalsIgnoreCase("true")) {
            return new GordianBoolean(true);
        } else if (s.equalsIgnoreCase("false")) {
            return new GordianBoolean(false);
        }

        if ((s.startsWith("\"") && s.endsWith("\""))
                || (s.startsWith("\'") && s.endsWith("\'"))) {
            return new GordianString(s.substring(1, s.length() - 1));
        }

        if (Strings.contains(s, '(') && Strings.contains(s, ')')
                && (s.indexOf('(') == 0 || !GordianRuntime.isValidCharacters("" + s.charAt(s.indexOf('(') - 1)))) {
            // Parentheses
            return scope.getInterpreter().interpretValue(
                    s.substring(0, s.indexOf('('))
                    + scope.getInterpreter().interpretValue(betweenMatch(s, '(', ')'))
                    + s.substring(betweenMatchLast(s, '(', ')') + 1));
        }

        if (s.startsWith("!")) {
            // Adjustments
            return new GordianBoolean(!((GordianBoolean) scope.getInterpreter().interpretValue(s.substring(1))).get());
        }

        // Methods
        if (s.indexOf("(") > 0 && s.indexOf(")") == s.length() - 1) {
            int c = 0;
            boolean method = false;
            for (int x = s.indexOf("("); x < s.length(); x++) {
                if (s.charAt(x) == '(') {
                    c++;
                } else if (s.charAt(x) == ')') {
                    c--;
                }
                if (c == 0) {
                    method = (x == s.length() - 1);
                    break;
                }
            }
            if (method) {
                Method m = scope.methods().get(s.substring(0, s.indexOf("(")));
                if (m != null) {
                    return m.run(scope, getArgs(betweenMatch(s, '(', ')')));
                }
            }
        }

        // Declarations
        if (Strings.contains(s, "++")) {
            s = s.substring(0, s.indexOf("++")) + "+=1" + s.substring(s.indexOf("++") + 2);
        }
        if (Strings.contains(s, "--")) {
            s = s.substring(0, s.indexOf("--")) + "-=1" + s.substring(s.indexOf("--") + 2);
        }
        Iterator i = GordianRuntime.operations.iterator();
        while (i.hasNext()) {
            Operator o = (Operator) i.next();
            String x = o.getChar() + "=";
            if (Strings.contains(s, x)) {
                s = s.substring(0, s.indexOf(x)) + "="
                        + s.substring(0, s.indexOf(x)) + o.getChar() + s.substring(s.indexOf(x) + 2);
            }
        }
        if (Strings.contains(s, "=")
                && GordianRuntime.isValidCharacters("" + s.charAt(s.indexOf("=") - 1))) {
            if (Strings.contains(s.substring(0, s.indexOf("=")), ".")) {
                Scope call;
                // Super
                if (s.substring(0, s.indexOf(".")).equals("super")) {
                    call = scope.parent();
                } else {
                    call = ((language.scope.Class) scope.storage().get(s.substring(0, s.indexOf("."))));
                }
                // Internal variable
                return call.getInterpreter().interpretValue(s.substring(s.indexOf(".") + 1, s.indexOf("=") + 1)
                        + scope.getInterpreter().interpretValue(s.substring(s.indexOf("=") + 1)).toString());
            }
            return new GordianDeclaration(scope).set(s.substring(0, s.indexOf("=")),
                    scope.getInterpreter().interpretValue(s.substring(s.indexOf("=") + 1)));
        }

        s = Strings.replaceAll(s, "--", "+");
        s = Strings.replaceAll(s, "+-", "-");
        s = Strings.replaceAll(s, "-+", "-");
        Iterator i1 = GordianRuntime.operations.iterator();
        while (i1.hasNext()) {
            // Calculations
            Operator o = (Operator) i1.next();
            String op = o.getChar() + "";
            if (Strings.contains(s, op) && !isBetween(s, o.getChar(), '(', ')')) {
                try {
                    return new GordianNumber(o.result(((GordianNumber) interpretValue(s.substring(0, s.lastIndexOf(o.getChar())))).getDouble(),
                            ((GordianNumber) interpretValue(s.substring(s.lastIndexOf(o.getChar()) + 1))).getDouble()));
                } catch (ClassCastException ex) {
                    // Was not a number... Try concatenation
                }
            }
        }

        // Constructors
        if (s.startsWith("[") && s.endsWith("]")) {
            Value v = scope.storage().get(s.substring(1, s.length() - 1));
            if (v != null) {
                return ((GordianClass) v).construct();
            }
        }

        // Comparisons
        if (Strings.contains(s, "&&")) {
            return new GordianBoolean(((GordianBoolean) scope.getInterpreter().interpretValue(s.substring(0, s.indexOf("&&")))).get()
                    && ((GordianBoolean) scope.getInterpreter().interpretValue(s.substring(s.indexOf("&&") + 2))).get());
        } else if (Strings.contains(s, "||")) {
            return new GordianBoolean(((GordianBoolean) scope.getInterpreter().interpretValue(s.substring(0, s.indexOf("||")))).get()
                    || ((GordianBoolean) scope.getInterpreter().interpretValue(s.substring(s.indexOf("||") + 2))).get());
        }

        if (Strings.contains(s, "==")) {
            return new GordianBoolean(scope.getInterpreter().interpretValue(s.substring(0, s.indexOf("==")))
                    .equals(scope.getInterpreter().interpretValue(s.substring(s.indexOf("==") + 2))));
        } else if (Strings.contains(s, "!=")) {
            return new GordianBoolean(!scope.getInterpreter().interpretValue(s.substring(0, s.indexOf("!=")))
                    .equals(scope.getInterpreter().interpretValue(s.substring(s.indexOf("!=") + 2))));
        }

        if (Strings.contains(s, ">=")) {
            return new GordianBoolean(((GordianNumber) scope.getInterpreter().interpretValue(s.substring(0, s.indexOf(">=")))).getDouble()
                    >= ((GordianNumber) scope.getInterpreter().interpretValue(s.substring(s.indexOf(">=") + 2))).getDouble());
        } else if (Strings.contains(s, "<=")) {
            return new GordianBoolean(((GordianNumber) scope.getInterpreter().interpretValue(s.substring(0, s.indexOf("<=")))).getDouble()
                    <= ((GordianNumber) scope.getInterpreter().interpretValue(s.substring(s.indexOf("<=") + 2))).getDouble());
        }

        if (Strings.contains(s, ">")) {
            return new GordianBoolean(((GordianNumber) scope.getInterpreter().interpretValue(s.substring(0, s.indexOf(">")))).getDouble()
                    > ((GordianNumber) scope.getInterpreter().interpretValue(s.substring(s.indexOf(">") + 1))).getDouble());
        } else if (Strings.contains(s, "<")) {
            return new GordianBoolean(((GordianNumber) scope.getInterpreter().interpretValue(s.substring(0, s.indexOf("<")))).getDouble()
                    < ((GordianNumber) scope.getInterpreter().interpretValue(s.substring(s.indexOf("<") + 1))).getDouble());
        }

        if (Strings.contains(s, "+")) {
            // Concatenations
            try {
                Value d1 = scope.getInterpreter().interpretValue(s.substring(0, s.indexOf("+")));
                Value d2 = scope.getInterpreter().interpretValue(s.substring(s.indexOf("+") + 1));

                return new GordianString(d1.toString() + d2.toString());
            } catch (Exception e) {
                // value was not two values
            }
        }

        // Object access
        if (s.indexOf(".") > 0 && s.indexOf(".") < s.length() - 1) {
            Scope call;
            // Super
            if (s.substring(0, s.indexOf(".")).equals("super")) {
                call = scope.parent();
            } else {
                call = ((language.scope.Class) scope.getInterpreter().interpretValue(s.substring(0, s.indexOf("."))));
            }
            String r = s.substring(s.indexOf(".") + 1);
            if (call != null) {
                return call.getInterpreter().interpretValue(r);
            }
        }

        // Variables
        Value v = scope.storage().get(s);
        if (v != null) {
            return v;
        }

        throw new NullPointerException("The value \"" + s + "\" could not be interpreted as a value.");
    }

    public Value[] interpretValues(String[] s) {
        Value[] v = new Value[s.length];
        for (int x = 0; x < v.length; x++) {
            v[x] = interpretValue(s[x]);
        }
        return v;
    }

    private boolean isBetween(String s, char i, char f, char l) {
        int pos = s.indexOf(i);
        int scope = 0;
        for (int x = 0; x < s.length(); x++) {
            if (s.charAt(x) == f) {
                scope++;
            } else if (s.charAt(x) == l) {
                scope--;
            }
            if (scope > 0 && Strings.contains(s, f) && s.indexOf(f) < x) {
                // in between
                if (x == pos) {
                    return true;
                }
            }
        }
        return false;
    }

    private String betweenMatch(String s, char f, char l) {
        int scope = 0;
        for (int x = 0; x < s.length(); x++) {
            if (s.charAt(x) == f) {
                scope++;
            } else if (s.charAt(x) == l) {
                scope--;
            }
            if (scope == 0 && Strings.contains(s, f) && s.indexOf(f) < x) {
                return s.substring(s.indexOf(f) + 1, x);
            }
        }
        return "";
    }

    private int betweenMatchLast(String s, char f, char l) {
        int scope = 0;
        for (int x = 0; x < s.length(); x++) {
            if (s.charAt(x) == f) {
                scope++;
            } else if (s.charAt(x) == l) {
                scope--;
            }
            if (scope == 0 && Strings.contains(s, f) && s.indexOf(f) < x) {
                return x;
            }
        }
        return -1;
    }

    private Value[] getArgs(String s) {
        if (!Strings.contains(s, '\"') && !Strings.contains(s, '(')) {
            return scope.getInterpreter().interpretValues(Strings.split(s, ','));
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
            Value[] args = new Value[l.size()];
            for (int x = 0; x < args.length; x++) {
                args[x] = scope.getInterpreter().interpretValue((String) l.get(x));
            }
            return args;
        }
    }
}
