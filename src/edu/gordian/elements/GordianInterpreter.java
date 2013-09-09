package edu.gordian.elements;

import edu.first.util.Strings;
import edu.first.util.list.ArrayList;
import edu.first.util.list.Iterator;
import edu.first.util.list.List;
import edu.gordian.instructions.GordianDeclaration;
import edu.gordian.scopes.GordianRuntime;
import edu.gordian.values.GordianBoolean;
import edu.gordian.values.GordianClass;
import edu.gordian.values.GordianList;
import edu.gordian.values.GordianNumber;
import edu.gordian.values.GordianString;
import language.instruction.Method;
import language.operator.Operator;
import language.scope.Instance;
import language.scope.Scope;
import language.value.Interpreter;
import language.value.Value;

public final class GordianInterpreter implements Interpreter {

    private final Scope scope;

    public GordianInterpreter(Scope scope) {
        this.scope = scope;
    }
    public ValueType[] valueTypes = new ValueType[]{
        new ValueType() {
            public Value from(String s) throws NoValue {
                GordianNumber n = GordianNumber.toNumber(s);
                if (n != null) {
                    return n;
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                if (s.equalsIgnoreCase("true")) {
                    return new GordianBoolean(true);
                } else if (s.equalsIgnoreCase("false")) {
                    return new GordianBoolean(false);
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                if ((s.startsWith("\"") && s.endsWith("\""))
                        || (s.startsWith("\'") && s.endsWith("\'"))) {
                    return new GordianString(s.substring(1, s.length() - 1));
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                if (Strings.contains(s, '(') && Strings.contains(s, ')')
                        && (s.indexOf('(') == 0 || !GordianRuntime.isValidCharacters("" + s.charAt(s.indexOf('(') - 1)))) {
                    return scope.getInterpreter().interpretValue(
                            s.substring(0, s.indexOf('('))
                            + scope.getInterpreter().interpretValue(betweenMatch(s, '(', ')'))
                            + s.substring(betweenMatchLast(s, '(', ')') + 1));
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                if (s.startsWith("!")) {
                    return new GordianBoolean(!((GordianBoolean) scope.getInterpreter().interpretValue(s.substring(1))).get());
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                if (s.indexOf("(") > 0 && s.lastIndexOf(')') == s.length() - 1) {
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

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                if (s.startsWith("{") && s.endsWith("}")) {
                    GordianList list = new GordianList(scope);
                    Value[] v = getArgs(betweenMatch(s, '{', '}'));
                    for (int x = 0; x < v.length; x++) {
                        list.add(v[x]);
                    }
                    return list;
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                if (s.endsWith("++") && GordianRuntime.isValidName(s.substring(0, s.indexOf("++")))) {
                    s = s.substring(0, s.indexOf("++")) + "+=1";
                }
                if (s.endsWith("--") && GordianRuntime.isValidName(s.substring(0, s.indexOf("--")))) {
                    s = s.substring(0, s.indexOf("--")) + "-=1";
                }
                Iterator i = GordianRuntime.operations.iterator();
                while (i.hasNext()) {
                    Operator o = (Operator) i.next();
                    String x = o.getChar() + "=";
                    if (Strings.contains(s, x) && GordianRuntime.isValidName(s.substring(0, s.indexOf(x)))) {
                        s = s.substring(0, s.indexOf(x)) + "="
                                + s.substring(0, s.indexOf(x)) + o.getChar() + s.substring(s.indexOf(x) + 2);
                    }
                }
                if (Strings.contains(s, "=")
                        && GordianRuntime.isValidName(s.substring(0, s.indexOf("=")))) {
                    if (Strings.contains(s.substring(0, s.indexOf("=")), ".")) {
                        Scope call;
                        // Super
                        if (s.substring(0, s.indexOf(".")).equals("super")) {
                            call = scope.parent();
                        } else {
                            call = ((Instance) scope.storage().get(s.substring(0, s.indexOf("."))));
                        }
                        // Internal variable
                        return call.getInterpreter().interpretValue(s.substring(s.indexOf(".") + 1, s.indexOf("="))
                                + scope.getInterpreter().interpretValue(s.substring(s.indexOf("="))).toString());
                    }
                    return new GordianDeclaration(scope).set(s.substring(0, s.indexOf("=")),
                            scope.getInterpreter().interpretValue(s.substring(s.indexOf("=") + 1)));
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                s = Strings.replaceAll(s, "--", "+");
                s = Strings.replaceAll(s, "+-", "-");
                s = Strings.replaceAll(s, "-+", "-");
                Iterator i1 = GordianRuntime.operations.iterator();
                while (i1.hasNext()) {
                    // Calculations
                    Operator o = (Operator) i1.next();
                    String op = o.getChar() + "";
                    if (Strings.contains(s, op)
                            && !isBetween(s, o.getChar(), '(', ')')
                            && !isBetween(s, o.getChar(), '[', ']')
                            && !isBetween(s, o.getChar(), '{', '}')) {
                        try {
                            return new GordianNumber(o.result(((GordianNumber) interpretValue(s.substring(0, s.lastIndexOf(o.getChar())))).getDouble(),
                                    ((GordianNumber) interpretValue(s.substring(s.lastIndexOf(o.getChar()) + 1))).getDouble()));
                        } catch (ClassCastException ex) {
                            // Was not a number... Try concatenation
                        }
                    }
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                if (s.startsWith("[") && s.endsWith("]") && GordianRuntime.isValidName(s.substring(1, s.length() - 1))) {
                    Value v = scope.getInterpreter().interpretValue(s.substring(1, s.length() - 1));
                    if (v != null) {
                        return ((GordianClass) v).construct();
                    }
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                if (Strings.contains(s, "&&")) {
                    return new GordianBoolean(((GordianBoolean) scope.getInterpreter().interpretValue(s.substring(0, s.indexOf("&&")))).get()
                            && ((GordianBoolean) scope.getInterpreter().interpretValue(s.substring(s.indexOf("&&") + 2))).get());
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                if (Strings.contains(s, "||")) {
                    return new GordianBoolean(((GordianBoolean) scope.getInterpreter().interpretValue(s.substring(0, s.indexOf("||")))).get()
                            || ((GordianBoolean) scope.getInterpreter().interpretValue(s.substring(s.indexOf("||") + 2))).get());
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                if (Strings.contains(s, "==")) {
                    return new GordianBoolean(scope.getInterpreter().interpretValue(s.substring(0, s.indexOf("==")))
                            .equals(scope.getInterpreter().interpretValue(s.substring(s.indexOf("==") + 2))));
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                if (Strings.contains(s, "!=")) {
                    return new GordianBoolean(!scope.getInterpreter().interpretValue(s.substring(0, s.indexOf("!=")))
                            .equals(scope.getInterpreter().interpretValue(s.substring(s.indexOf("!=") + 2))));
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                if (Strings.contains(s, ">=")) {
                    return new GordianBoolean(((GordianNumber) scope.getInterpreter().interpretValue(s.substring(0, s.indexOf(">=")))).getDouble()
                            >= ((GordianNumber) scope.getInterpreter().interpretValue(s.substring(s.indexOf(">=") + 2))).getDouble());
                } else if (Strings.contains(s, "<=")) {
                    return new GordianBoolean(((GordianNumber) scope.getInterpreter().interpretValue(s.substring(0, s.indexOf("<=")))).getDouble()
                            <= ((GordianNumber) scope.getInterpreter().interpretValue(s.substring(s.indexOf("<=") + 2))).getDouble());
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                if (Strings.contains(s, ">")) {
                    return new GordianBoolean(((GordianNumber) scope.getInterpreter().interpretValue(s.substring(0, s.indexOf(">")))).getDouble()
                            > ((GordianNumber) scope.getInterpreter().interpretValue(s.substring(s.indexOf(">") + 1))).getDouble());
                } else if (Strings.contains(s, "<")) {
                    return new GordianBoolean(((GordianNumber) scope.getInterpreter().interpretValue(s.substring(0, s.indexOf("<")))).getDouble()
                            < ((GordianNumber) scope.getInterpreter().interpretValue(s.substring(s.indexOf("<") + 1))).getDouble());
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
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

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                if (s.indexOf(".") > 0 && s.indexOf(".") < s.length() - 1) {
                    Scope call;
                    // Super
                    if (s.substring(0, s.lastIndexOf('.')).equals("super")) {
                        call = scope.parent();
                    } else {
                        call = ((Scope) scope.getInterpreter().interpretValue(s.substring(0, s.lastIndexOf('.'))));
                    }
                    String r = s.substring(s.lastIndexOf('.') + 1);
                    if (Strings.contains(r.substring(r.lastIndexOf('.') + 1), "(")
                            && Strings.contains(r.substring(r.lastIndexOf('.') + 1), ")")) {
                        return call.methods().get(r.substring(0, r.indexOf("("))).run(scope, getArgs(betweenMatch(s, '(', ')')));
                    }

                    if (call != null) {
                        return call.getInterpreter().interpretValue(r);
                    }
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                if (s.indexOf("[") > 0 && s.endsWith("]") && GordianRuntime.isValidName(s.substring(0, s.indexOf("[")))) {
                    return ((GordianList) scope.getInterpreter().interpretValue(s.substring(0, s.lastIndexOf('['))))
                            .get(((GordianNumber) scope.getInterpreter().interpretValue(betweenMatchReverse(s, '[', ']'))).getInt());
                }

                throw new NoValue();
            }
        }, new ValueType() {
            public Value from(String s) throws NoValue {
                Value v = scope.storage().get(s);
                if (v != null) {
                    return v;
                }

                throw new NoValue();
            }
        }};

    public Value interpretValue(String s) {
        if (Strings.isEmpty(s)) {
            return new GordianString(s);
        }

        for (int x = 0; x < valueTypes.length; x++) {
            try {
                return valueTypes[x].from(s);
            } catch (NoValue e) {
                // Keep moving along...
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

    private String betweenMatchReverse(String s, char f, char l) {
        int scope = 0;
        for (int x = s.length() - 1; x >= 0; x--) {
            if (s.charAt(x) == f) {
                scope--;
            } else if (s.charAt(x) == l) {
                scope++;
            }
            if (scope == 0 && Strings.contains(s, l) && s.lastIndexOf(l) > x) {
                return s.substring(x + 1, s.lastIndexOf(l));
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
        if (!Strings.contains(s, '\"') && !Strings.contains(s, "\'") && !Strings.contains(s, '(') && !Strings.contains(s, '{')) {
            return scope.getInterpreter().interpretValues(Strings.split(s, ','));
        } else {
            List l = new ArrayList();
            boolean inQuotes = false;
            int p1 = 0, p2 = 0;
            int last = 0;
            char[] d = s.toCharArray();
            for (int x = 0; x < d.length; x++) {
                if ((d[x] == '\"' || d[x] == '\'') && (x == 0 || d[x - 1] != '\\')) {
                    inQuotes = !inQuotes;
                } else if (d[x] == '(') {
                    p1++;
                } else if (d[x] == ')') {
                    p1--;
                } else if (d[x] == '{') {
                    p2++;
                } else if (d[x] == '}') {
                    p2--;
                }
                if (d[x] == ',' && !inQuotes && p1 == 0 && p2 == 0 && !Strings.isEmpty(s.substring(last, x))) {
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

    private static interface ValueType {

        public Value from(String s) throws NoValue;
    }

    private static class NoValue extends Exception {
    }
}
