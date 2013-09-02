package edu.gordian.elements;

import edu.first.util.Strings;
import edu.first.util.list.Iterator;
import language.instruction.Method;
import edu.gordian.instructions.GordianDeclaration;
import language.operator.Operator;
import language.scope.Scope;
import edu.gordian.scopes.GordianRuntime;
import language.value.Interpreter;
import language.value.Value;
import edu.gordian.values.GordianBoolean;
import edu.gordian.values.GordianNumber;
import edu.gordian.values.GordianString;

public class GordianInterpreter implements Interpreter {

    private final Scope scope;

    public GordianInterpreter(Scope scope) {
        this.scope = scope;
    }

    public Value interpretValue(String s) {
        if (Strings.isEmpty(s)) {
            return null;
        }

        GordianNumber n = GordianNumber.toNumber(s);
        if (n != null) {
            return n;
        }

        if (s.equalsIgnoreCase("true")) {
            return new GordianBoolean(true);
        } else if (s.equalsIgnoreCase("false")) {
            return new GordianBoolean(false);
        }

        if ((s.startsWith("\"") && s.endsWith("\"") && s.substring(1).indexOf("\"") == s.length() - 2)
                || (s.startsWith("\'") && s.endsWith("\'") && s.substring(1).indexOf("\'") == s.length() - 2)) {
            return new GordianString(s.substring(1, s.length() - 1));
        }

        if (s.startsWith("!")) {
            return new GordianBoolean(!((GordianBoolean) scope.getInterpreter().interpretValue(s.substring(1))).get());
        } else if (s.startsWith("-")) {
            return new GordianNumber(-((GordianNumber) scope.getInterpreter().interpretValue(s.substring(1))).getDouble());
        } else if (s.startsWith("+")) {
            return new GordianNumber(((GordianNumber) scope.getInterpreter().interpretValue(s.substring(1))).getDouble());
        }

        if (s.indexOf("(") > 0 && s.charAt(s.length() - 1) == ')') {
            Method m = scope.methods().get(s.substring(0, s.indexOf("(")));
            if (m != null) {
                return m.run(scope.getInterpreter().interpretValues(Strings.split(s.substring(s.indexOf("(") + 1, s.lastIndexOf(')')), ",")));
            }
        }
        Value v = scope.storage().get(s);
        if (v != null) {
            return v;
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
                && !(Strings.contains(s, "==") && s.indexOf("=") == s.indexOf("=="))
                && !(Strings.contains(s, "!=") && s.indexOf("=") == s.indexOf("!=") - 1)
                && !(Strings.contains(s, ">=") && s.indexOf("=") == s.indexOf(">=") - 1)
                && !(Strings.contains(s, "<=") && s.indexOf("=") == s.indexOf("<=") - 1)) {
            return new GordianDeclaration(scope).set(s.substring(0, s.indexOf("=")),
                    scope.getInterpreter().interpretValue(s.substring(s.indexOf("=") + 1)));
        } else if (s.endsWith("++")) {
            GordianNumber h = (GordianNumber) scope.storage().get(s.substring(0, s.indexOf("++")));
            if (h == null) {
                h = new GordianNumber(0);
            }
            return new GordianDeclaration(scope).set(s.substring(0, s.indexOf("++")),
                    new GordianNumber(h.getDouble() + 1));
        } else if (s.endsWith("--")) {
            GordianNumber h = (GordianNumber) scope.storage().get(s.substring(0, s.indexOf("--")));
            if (h == null) {
                h = new GordianNumber(0);
            }
            return new GordianDeclaration(scope).set(s.substring(0, s.indexOf("--")),
                    new GordianNumber(h.getDouble() - 1));
        }

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
            Value d1 = scope.getInterpreter().interpretValue(s.substring(0, s.indexOf("+")));
            Value d2 = scope.getInterpreter().interpretValue(s.substring(s.indexOf("+") + 1));

            if (!(d1 instanceof GordianNumber && d2 instanceof GordianNumber)) {
                return new GordianString(d1.toString() + d2.toString());
            }
        }

        Iterator i1 = GordianRuntime.operations.iterator();
        while (i1.hasNext()) {
            Operator o = (Operator) i1.next();
            String op = o.getChar() + "";
            if (Strings.contains(s, op)) {
                return new GordianNumber(o.result(((GordianNumber) interpretValue(s.substring(0, s.indexOf(op)))).getDouble(),
                        ((GordianNumber) interpretValue(s.substring(s.indexOf(op) + 1))).getDouble()));
            }
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
}
