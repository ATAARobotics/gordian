package edu.gordian;

import com.sun.squawk.util.StringTokenizer;
import edu.gordian.elements.methods.Method;
import edu.gordian.elements.methods.MethodBase;
import edu.gordian.elements.methods.ReturningMethod;
import edu.gordian.elements.methods.UserMethod;
import edu.gordian.scopes.For;
import edu.gordian.scopes.If;
import edu.gordian.scopes.While;
import edu.gordian.values.ReturningMethodBase;
import edu.gordian.values.StaticValue;
import edu.gordian.values.UserReturningMethod;
import edu.gordian.values.Value;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

public class Scope {

    private final MapGroup publicVars;
    private final MapGroup publicMethods;
    private final Hashtable publicReturning;
    private final Hashtable privateVars;
    private final Hashtable privateMethods;

    public Scope() {
        publicMethods = new MapGroup(new Hashtable());
        publicReturning = new Hashtable(0);
        publicVars = new MapGroup(new Hashtable());
        privateVars = new Hashtable();
        privateMethods = new Hashtable();
    }

    public Scope(UserMethod[] methods, UserReturningMethod[] returning) {
        publicMethods = new MapGroup(new Hashtable());
        for (int x = 0; x < methods.length; x++) {
            publicMethods.put(methods[x].getName(), methods[x]);
        }
        publicReturning = new Hashtable(returning.length);
        for (int x = 0; x < returning.length; x++) {
            publicReturning.put(returning[x].getName(), returning[x]);
        }

        publicVars = new MapGroup(new Hashtable());
        privateVars = new Hashtable();
        privateMethods = new Hashtable();
    }

    public Scope(Scope scope) {
        publicVars = new MapGroup(scope.publicVars);
        publicMethods = new MapGroup(scope.publicMethods);
        publicReturning = scope.publicReturning;
        privateVars = new Hashtable();
        privateMethods = new Hashtable();

        publicVars.add(scope.privateVars);
        publicMethods.add(scope.privateMethods);
    }

    public Value toValue(String e) {
        try {
            return new StaticValue(Double.valueOf(e));
        } catch (NumberFormatException ex) {
        }
        if (e.toLowerCase().equals("true")) {
            return new StaticValue(Boolean.TRUE);
        }
        if (e.toLowerCase().equals("false")) {
            return new StaticValue(Boolean.FALSE);
        }
        if (e.startsWith("!")) {
            return new StaticValue(Boolean.valueOf(!(((Boolean) toValue(e.substring(1)).getValue()).booleanValue())));
        }
        if (Strings.contains(e, "==")) {
            return new Equals(e);
        }
        if (Strings.contains(e, "!=")) {
            return new NotEquals(e);
        }
        if (Strings.contains(e, ">=")) {
            return new GreaterThanOrEqualTo(e);
        }
        if (Strings.contains(e, "<=")) {
            return new LessThanOrEqualTo(e);
        }
        if (Strings.contains(e, ">")) {
            return new GreaterThan(e);
        }
        if (Strings.contains(e, "<")) {
            return new LessThan(e);
        }

        if (privateVars.containsKey(e)) {
            return (Value) privateVars.get(e);
        }
        if (publicVars.containsKey(e)) {
            return (Value) publicVars.get(e);
        }
        if (Strings.containsThatIsnt(e, '-', "--")) {
            return new Subtraction(e.substring(0, Strings.indexThatIsnt(e, '-', "--")),
                    e.substring(Strings.indexThatIsnt(e, '-', "--") + 1));
        }
        if (Strings.containsThatIsnt(e, '+', "++")) {
            return new Addition(e.substring(0, Strings.indexThatIsnt(e, '+', "++")),
                    e.substring(Strings.indexThatIsnt(e, '+', "++") + 1));
        }
        if (Strings.contains(e, '/')) {
            return new Division(e.substring(0, e.indexOf('/')),
                    e.substring(e.indexOf('/') + 1));
        }
        if (Strings.contains(e, '*')) {
            return new Multiplication(e.substring(0, e.indexOf('*')),
                    e.substring(e.indexOf('*') + 1));
        }
        if (Strings.contains(e, '%')) {
            return new Modulus(e.substring(0, e.indexOf('%')),
                    e.substring(e.indexOf('%') + 1));
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
        if (Strings.contains(e, "++")) {
            return new Increment(e.substring(0, e.indexOf("++")), +1);
        }
        if (Strings.contains(e, "--")) {
            return new Increment(e.substring(0, e.indexOf("--")), -1);
        }

        if(Strings.contains(e, "\"")) {
            e = e.substring(1, e.length() - 1);
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
        if (Strings.contains(e, '=')
                && e.indexOf('=') != e.indexOf("!=")
                && e.indexOf('=') != e.indexOf("==")
                && e.indexOf('=') != e.indexOf("<=")
                && e.indexOf('=') != e.indexOf(">=")) {
            // DECLARATION
            return new Declaration(e.substring(0, e.indexOf('=')), e.substring(e.indexOf('=') + 1));
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
        if (Strings.contains(e, "++")) {
            return new Increment(e.substring(0, e.indexOf("++")), +1);
        }
        if (Strings.contains(e, "--")) {
            return new Increment(e.substring(0, e.indexOf("--")), -1);
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
        script = Strings.replaceAll(script, '\n', ';');
        if (Strings.contains(script, ' ')) {
            script = removeSpaces(script, 0);
        }
        while (Strings.contains(script, '#')) {
            String toRemove = script.substring(script.indexOf('#'), (script.substring(script.indexOf('#'))).indexOf(';') + script.indexOf('#'));
            script = Strings.replace(script, toRemove, "");
        }
        StringTokenizer t = new StringTokenizer(script, ";");
        int line = 0;
        int scopes = 0;
        String scope = "";
        while (t.hasMoreElements()) {
            String next = t.nextToken();
            if (Strings.isEmpty(next)) {
                continue;
            }
            line++;

            if (next.toLowerCase().startsWith("while") || next.toLowerCase().startsWith("for")
                    || next.toLowerCase().startsWith("if")) {
                scopes++;
            }
            if (next.toLowerCase().startsWith("end")) {
                scopes--;
            }
            if (scopes > 0) {
                scope += next + ';';
                continue;
            }

            if (scopes == 0 && scope.startsWith("while")) {
                String start = scope.substring(0, scope.indexOf(';'));
                String s = scope.substring(scope.indexOf(';') + 1);
                new While(start.substring(start.indexOf('(') + 1, start.lastIndexOf(')')), this).run(s);
                scope = "";
                continue;
            }
            if (scopes == 0 && scope.startsWith("if")) {
                String start = scope.substring(0, scope.indexOf(';'));
                String s = scope.substring(scope.indexOf(';') + 1);
                new If(start.substring(start.indexOf('(') + 1, start.lastIndexOf(')')), this).run(s);
                scope = "";
                continue;
            }
            if (scopes == 0 && scope.startsWith("for")) {
                String start = scope.substring(0, scope.indexOf(';'));
                String s = scope.substring(scope.indexOf(';') + 1);
                new For(start.substring(start.indexOf('(') + 1, start.lastIndexOf(')')), this).run(s);
                scope = "";
                continue;
            }

            try {
                toElement(next).run();
            } catch (Exception ex) {
                throw new Exception("LINE " + line + " - " + ex.getClass().getName() + ": " + ex.getMessage());
            }
        }
    }

    private static Double toNumber(Value v) {
        Object o = v.getValue();
        if (o instanceof Double) {
            return (Double) o;
        } else {
            return Double.valueOf(0);
        }
    }

    private final class Declaration implements Element {

        private final String key;
        private final String value;

        public Declaration(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public void run() {
            if (publicVars.containsKey(key)) {
                publicVars.put(key, toValue(value));
            } else {
                privateVars.put(key, toValue(value));
            }
        }
    }

    private final class Increment implements Element, Value {

        private final String key;
        private final int i;

        public Increment(String key, int i) {
            this.key = key;
            this.i = i;
        }

        public void run() {
            getValue();
        }

        public Object getValue() {
            int value;
            if (publicVars.containsKey(key)) {
                int old = toNumber((Value) publicVars.get(key)).intValue();
                publicVars.put(key, new StaticValue(Double.valueOf(old + i)));
                value = old + i;
            } else if (privateVars.containsKey(key)) {
                int old = toNumber((Value) privateVars.get(key)).intValue();
                privateVars.put(key, new StaticValue(Double.valueOf(old + i)));
                value = old + i;
            } else {
                privateVars.put(key, new StaticValue(Double.valueOf(0 + i)));
                value = 0 + i;
            }
            return Double.valueOf(value);
        }

        public String toString() {
            return getValue().toString();
        }
    }

    private abstract class Calculation implements Value {

        private Double value;

        public Calculation(String first, String second) {
            value = getNumber(toNumber(toValue(first)),
                    toNumber(toValue(second)));
        }

        public Object getValue() {
            return value;
        }

        public abstract Double getNumber(Double f1, Double f2);

        public String toString() {
            return getValue().toString();
        }
    }

    private final class Addition extends Calculation {

        public Addition(String first, String second) {
            super(first, second);
        }

        public Double getNumber(Double f1, Double f2) {
            return Double.valueOf(f1.doubleValue() + f2.doubleValue());
        }
    }

    private final class Subtraction extends Calculation {

        public Subtraction(String first, String second) {
            super(first, second);
        }

        public Double getNumber(Double f1, Double f2) {
            return Double.valueOf(f1.doubleValue() - f2.doubleValue());
        }
    }

    private final class Multiplication extends Calculation {

        public Multiplication(String first, String second) {
            super(first, second);
        }

        public Double getNumber(Double f1, Double f2) {
            return Double.valueOf(f1.doubleValue() * f2.doubleValue());
        }
    }

    private final class Division extends Calculation {

        public Division(String first, String second) {
            super(first, second);
        }

        public Double getNumber(Double f1, Double f2) {
            return Double.valueOf(f1.doubleValue() / f2.doubleValue());
        }
    }

    private final class Modulus extends Calculation {

        public Modulus(String first, String second) {
            super(first, second);
        }

        public Double getNumber(Double f1, Double f2) {
            return Double.valueOf(f1.doubleValue() % f2.doubleValue());
        }
    }

    private abstract class Comparison implements Value {

        private final String full;
        private final String symbol;

        public Comparison(String full, String symbol) {
            this.full = full;
            this.symbol = symbol;
        }

        public Object getValue() {
            return Boolean.valueOf(get(toValue(full.substring(0, full.indexOf(symbol))).getValue(),
                    toValue(full.substring(full.indexOf(symbol) + symbol.length())).getValue()));
        }

        public abstract boolean get(Object f1, Object f2);

        public String toString() {
            return getValue().toString();
        }
    }

    private final class Equals extends Comparison {

        public Equals(String full) {
            super(full, "==");
        }

        public boolean get(Object f1, Object f2) {
            return f1.equals(f2);
        }
    }

    private final class NotEquals extends Comparison {

        public NotEquals(String full) {
            super(full, "!=");
        }

        public boolean get(Object f1, Object f2) {
            return !f1.equals(f2);
        }
    }

    private abstract class NumberComparison extends Comparison {

        public NumberComparison(String full, String symbol) {
            super(full, symbol);
        }

        public boolean get(Object f1, Object f2) {
            return get((Double) f1, (Double) f2);
        }

        public abstract boolean get(Double f1, Double f2);
    }

    private final class GreaterThan extends NumberComparison {

        public GreaterThan(String full) {
            super(full, ">");
        }

        public boolean get(Double f1, Double f2) {
            return f1.doubleValue() > f2.doubleValue();
        }
    }

    private final class LessThan extends NumberComparison {

        public LessThan(String full) {
            super(full, "<");
        }

        public boolean get(Double f1, Double f2) {
            return f1.doubleValue() < f2.doubleValue();
        }
    }

    private final class GreaterThanOrEqualTo extends NumberComparison {

        public GreaterThanOrEqualTo(String full) {
            super(full, ">=");
        }

        public boolean get(Double f1, Double f2) {
            return f1.doubleValue() >= f2.doubleValue();
        }
    }

    private final class LessThanOrEqualTo extends NumberComparison {

        public LessThanOrEqualTo(String full) {
            super(full, "<=");
        }

        public boolean get(Double f1, Double f2) {
            return f1.doubleValue() <= f2.doubleValue();
        }
    }

    private static final class MapGroup {

        private final Vector maps = new Vector();

        public MapGroup(Hashtable start) {
            maps.addElement(start);
        }

        public MapGroup(MapGroup group) {
            for (int x = 0; x < group.maps.size(); x++) {
                maps.addElement(group.maps.elementAt(x));
            }
        }

        public void add(Hashtable h) {
            maps.addElement(h);
        }

        public Enumeration keys() {
            return new Enumeration() {
                private final Enumeration[] enumerations;
                private int i;

                {
                    enumerations = new Enumeration[maps.size()];
                    for (int x = 0; x < maps.size(); x++) {
                        enumerations[x] = ((Hashtable) maps.elementAt(x)).keys();
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

        public Enumeration elements() {
            return new Enumeration() {
                private final Enumeration[] enumerations;
                private int i;

                {
                    enumerations = new Enumeration[maps.size()];
                    for (int x = 0; x < maps.size(); x++) {
                        enumerations[x] = ((Hashtable) maps.elementAt(x)).elements();
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
                if (((Hashtable) maps.elementAt(x)).contains(value)) {
                    return true;
                }
            }
            return false;
        }

        public boolean containsKey(Object key) {
            for (int x = 0; x < maps.size(); x++) {
                if (((Hashtable) maps.elementAt(x)).containsKey(key)) {
                    return true;
                }
            }
            return false;
        }

        public Object get(Object key) {
            for (int x = 0; x < maps.size(); x++) {
                if (((Hashtable) maps.elementAt(x)).containsKey(key)) {
                    return ((Hashtable) maps.elementAt(x)).get(key);
                }
            }
            return null;
        }

        public Object put(Object key, Object value) {
            for (int x = 0; x < maps.size(); x++) {
                if (((Hashtable) maps.elementAt(x)).containsKey(key)) {
                    return ((Hashtable) maps.elementAt(x)).put(key, value);
                }
            }
            return ((Hashtable) maps.elementAt(maps.size() - 1)).put(key, value);
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
                s += ((Hashtable) maps.elementAt(x)).size();
            }
            return s;
        }

        public boolean isEmpty() {
            return size() == 0;
        }
    }
}
