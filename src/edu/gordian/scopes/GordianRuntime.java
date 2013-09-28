package edu.gordian.scopes;

import com.sun.squawk.util.StringTokenizer;
import edu.first.util.Strings;
import edu.first.util.list.Collections;
import edu.first.util.list.List;
import edu.gordian.elements.GordianAnalyser;
import edu.gordian.elements.GordianInterpreter;
import language.element.Analyser;
import language.value.Interpreter;
import edu.gordian.internal.GordianMethods;
import edu.gordian.internal.GordianStorage;
import language.operator.Operator;
import language.scope.Scope;
import language.internal.Methods;
import language.internal.Storage;

public final class GordianRuntime implements Scope {

    private final Analyser analyser = new GordianAnalyser(this);
    private final Interpreter interpreter = new GordianInterpreter(this);
    private final GordianMethods methods = new GordianMethods();
    private final GordianStorage storage = new GordianStorage();
    public static final List operations = Collections.asList(new Operator[]{
        new Addition(), new Subtraction(), new Multiplication(), new Division(), new Modulus()
    });

    public Scope container() {
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
        s = convertStrings(s);
        if (!s.endsWith(";")) {
            s = s + ";";
        }
        s = Strings.replaceAll(s, "\n", ";");
        if (Strings.contains(s, " ")) {
            s = removeSpaces(s, 0);
        }
        while (Strings.contains(s, "#")) {
            String toRemove = s.substring(s.indexOf('#'), (s.substring(s.indexOf('#'))).indexOf(';') + s.indexOf('#'));
            s = Strings.replace(s, toRemove, "");
        }
        return s;
    }

    private static String convertStrings(String s) {
        char[] c = s.toCharArray();
        StringBuffer b = new StringBuffer();

        int debug1 = -1;
        boolean i1 = false, i2 = false;
        for (int x = 0; x < c.length; x++) {
            String add = String.valueOf(c[x]);
            if (c[x] == '\'' && (x == 0 || (c[x - 1] != '\\' && c[x - 1] != '!' && c[x + 1] != '!'))) {
                i1 = !i1;
                if (i1) {
                    debug1 = x;
                    add = "\'!";
                } else {
                    add = "!\'";
                }
            } else if (c[x] == '\"' && (x == 0 || (c[x - 1] != '\\' && c[x - 1] != '!' && c[x + 1] != '!'))) {
                i2 = !i2;
                if (i2) {
                    debug1 = x;
                    add = "\'!";
                } else {
                    add = "!\'";
                }
            } else if (i1 || i2) {
                add = "@" + (int) c[x];
            }
            b.append(add);
        }

        if (i1 || i2) {
            throw new IllegalStateException("Quotes were not closed! - " + s.substring(debug1));
        }

        return b.toString();
    }

    private static String removeSpaces(final String s, int x) {
        String a = Strings.replaceAll(s, "\t", " ");

        boolean inQuotes = false;
        x += a.substring(x).indexOf(' ');
        for (int i = 0; i < x; i++) {
            if (a.charAt(i) == '\"' || a.charAt(i) == '\'') {
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
