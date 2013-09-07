package edu.gordian.scopes;

import com.sun.squawk.util.StringTokenizer;
import edu.first.util.Strings;
import edu.first.util.list.Collections;
import edu.first.util.list.List;
import edu.gordian.elements.GordianAnalyser;
import edu.gordian.elements.GordianInterpreter;
import language.element.Analyser;
import language.value.Interpreter;
import language.value.Value;
import language.instruction.Method;
import edu.gordian.internal.GordianMethods;
import edu.gordian.internal.GordianStorage;
import edu.gordian.internal.ValueReturned;
import edu.gordian.values.GordianBoolean;
import language.operator.Operator;
import edu.gordian.values.GordianNull;
import language.scope.Scope;
import edu.gordian.values.GordianNumber;
import edu.gordian.values.GordianString;
import java.util.Random;
import language.internal.Methods;
import language.internal.Storage;

public final class GordianRuntime implements Scope {

    private final Analyser analyser = new GordianAnalyser(this);
    private final Interpreter interpreter = new GordianInterpreter(this);
    private final GordianMethods methods = new GordianMethods();
    private final GordianStorage storage = new GordianStorage();
    private static final Random RANDOM = new Random();
    public static final List operations = Collections.asList(new Operator[]{
        new Addition(), new Subtraction(), new Multiplication(), new Division(), new Modulus()
    });

    {
        storage.set("null", GordianNull.get());
        methods.put("return", new Method() {
            public Value run(Scope current, Value[] args) {
                throw new ValueReturned(args[0]);
            }
        });
        methods.put("delete", new Method() {
            public Value run(Scope current, Value[] args) {
                return current.storage().remove(((GordianString) args[0]).toString());
            }
        });
        methods.put("int", new Method() {
            public Value run(Scope current, Value[] args) {
                if (args[0] instanceof GordianNumber) {
                    return new GordianNumber(((GordianNumber) args[0]).getInt());
                } else {
                    return new GordianNumber(GordianNumber.toNumber(args[0].toString()).getInt());
                }
            }
        });
        methods.put("num", new Method() {
            public Value run(Scope current, Value[] args) {
                if (args[0] instanceof GordianNumber) {
                    return new GordianNumber(((GordianNumber) args[0]).getDouble());
                } else {
                    return GordianNumber.toNumber(args[0].toString());
                }
            }
        });
        methods.put("bool", new Method() {
            public Value run(Scope current, Value[] args) {
                if (args[0] instanceof GordianBoolean) {
                    return new GordianBoolean(((GordianBoolean) args[0]).get());
                } else {
                    return GordianBoolean.toBoolean(args[0].toString());
                }
            }
        });
        methods.put("str", new Method() {
            public Value run(Scope current, Value[] args) {
                return new GordianString(args[0].toString());
            }
        });
        methods.put("print", new Method() {
            public Value run(Scope current, Value[] args) {
                if (args.length > 1) {
                    System.out.println(Collections.asList(args));
                } else {
                    System.out.println(args[0]);
                }
                return null;
            }
        });
        methods.put("sleep", new Method() {
            public Value run(Scope current, Value[] args) {
                try {
                    Thread.sleep(((GordianNumber) args[0]).getLong());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        });
        methods.put("rand", new Method() {
            public Value run(Scope current, Value[] args) {
                return new GordianNumber(RANDOM.nextDouble());
            }
        });
        methods.put("randint", new Method() {
            public Value run(Scope current, Value[] args) {
                return new GordianNumber(RANDOM.nextInt());
            }
        });
        methods.put("neg", new Method() {
            public Value run(Scope current, Value[] args) {
                return new GordianNumber(-((GordianNumber) args[0]).getDouble());
            }
        });
    }

    public Scope parent() {
        return null;
    }

    public static void testName(String s) {
        if (!isValidName(s)) {
            throw new IllegalStateException("\"" + s + "\" is not a valid variable/method name.");
        }
    }

    public static boolean isValidName(String s) {
        return !(Strings.isEmpty(s)
                || s.equalsIgnoreCase("true")
                || s.equalsIgnoreCase("false"))
                && isValidCharacters(s);
    }

    public static boolean isValidCharacters(String s) {
        for (int x = 0; x < s.length(); x++) {
            if (!( // Letters
                    (Character.isLowerCase(s.charAt(x)) || Character.isUpperCase(s.charAt(x)))
                    // Numbers
                    || (s.charAt(x) > 47 && s.charAt(x) < 58))) {
                return false;
            }
        }
        return true;
    }

    public static void run(Scope s, String i) {
        i = pre(i);
        if ((i.indexOf(";") == i.lastIndexOf(';')) && (i.indexOf(";") == i.length() - 1)) {
            s.getAnalyser().analyseInstruction(i.substring(0, i.length() - 1));
        } else {
            StringTokenizer tokenizer = new StringTokenizer(i, ";");
            while (tokenizer.hasMoreElements()) {
                String t = tokenizer.nextToken();
                if (t.endsWith(":")) {
                    StringBuffer buffer = new StringBuffer(t).append(";");
                    int scopes = 1;
                    while (tokenizer.hasMoreElements() && !(scopes == 0)) {
                        t = tokenizer.nextToken();
                        buffer.append(t).append(";");
                        if (t.endsWith(":")) {
                            scopes++;
                        } else if (t.equals("fi")) {
                            scopes--;
                        }
                    }
                    String f = buffer.toString();
                    if (f.endsWith("fi;")) {
                        f = f.substring(0, f.length() - 3);
                    }
                    s.getAnalyser().analyseBlock(f);
                } else {
                    s.run(t + ";");
                }
            }
        }
    }

    public void run(String i) {
        run(this, i);
    }

    public Analyser getAnalyser() {
        return analyser;
    }

    public Interpreter getInterpreter() {
        return interpreter;
    }

    public Methods methods() {
        return methods;
    }

    public Storage storage() {
        return storage;
    }

    private static String pre(String s) {
        if (!s.endsWith(";")) {
            s = s + ";";
        }
        s = Strings.replaceAll(s, "\n", ";");
        s = Strings.replaceAll(s, ":", ":;");
        if (Strings.contains(s, " ")) {
            s = removeSpaces(s, 0);
        }
        while (Strings.contains(s, "#")) {
            String toRemove = s.substring(s.indexOf('#'), (s.substring(s.indexOf('#'))).indexOf(';') + s.indexOf('#'));
            s = Strings.replace(s, toRemove, "");
        }
        return s;
    }

    private static String removeSpaces(final String s, int x) {
        String a = Strings.replaceAll(s, "\t", " ");

        boolean inQuotes = false;
        x += a.substring(x).indexOf(' ');
        for (int i = 0; i < x; i++) {
            if ((a.charAt(i) == '\"' || a.charAt(i) == '\'') && a.charAt(i - 1) != '\\') {
                inQuotes = !inQuotes;
            } else if (a.charAt(i) == ';') {
                inQuotes = false;
            }
        }
        if (!inQuotes) {
            a = a.substring(0, x) + s.substring(x + 1);
        } else {
            if (a.substring(x + 1).indexOf(' ') == -1) {
                x = a.length() - 1;
            } else {
                x = a.substring(x + 1).indexOf(' ') + x + 1;
            }
        }

        if (Strings.contains(a.substring(x), " ")) {
            return removeSpaces(a, x);
        }

        return a;
    }

    private static class Addition implements Operator {

        public char getChar() {
            return '+';
        }

        public double result(double o, double o1) {
            return o + o1;
        }
    }

    private static class Subtraction implements Operator {

        public char getChar() {
            return '-';
        }

        public double result(double o, double o1) {
            return o - o1;
        }
    }

    private static class Multiplication implements Operator {

        public char getChar() {
            return '*';
        }

        public double result(double o, double o1) {
            return o * o1;
        }
    }

    private static class Division implements Operator {

        public char getChar() {
            return '/';
        }

        public double result(double o, double o1) {
            return o / o1;
        }
    }

    private static class Modulus implements Operator {

        public char getChar() {
            return '%';
        }

        public double result(double o, double o1) {
            return o % o1;
        }
    }
}
