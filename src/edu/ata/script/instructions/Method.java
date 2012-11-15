package edu.ata.script.instructions;

import java.util.Vector;
import edu.ata.script.Instruction;
import edu.ata.script.InvalidStatementException;
import edu.ata.script.Statement;
import edu.ata.script.StringUtils;
import edu.ata.script.Value;
import edu.ata.script.instructions.methods.Keywords;
import edu.ata.script.instructions.methods.NativeMethods;

/**
 * Encompassing class of all methods.
 *
 * @author joel
 */
public abstract class Method extends Instruction {

    /**
     * Hide this method to use the class. <i><b>This method is meant to be
     * hidden by it's subclasses. It is called in a tree-like structure down the
     * children of {@link Statement}.</i></b>
     *
     * @param statement the statement to analyze
     * @return whether it is a valid instruction
     */
    public static boolean isValid(String statement) {
        if (statement.indexOf("(") < 0 || statement.indexOf(")") < 0) {
            return false;
        } else {
            try {
                getStatementFrom(statement);
                return true;
            } catch (InvalidStatementException ex) {
                return false;
            }
        }
    }

    /**
     * Hide this method to use the class. <i><b>This method is meant to be
     * hidden by it's subclasses. It is called in a tree-like structure down the
     * children of {@link Statement}.</i></b>
     *
     * <p> It is very important to verify that the statement is valid before
     * using this method. If it is not valid, it will throw
     * {@link InvalidStatementException}. </p>
     *
     * @param statement the statement to analyze
     * @return a {@link Statement} object of the type
     * @throws InvalidStatementException thrown when statement is unrecognizable
     */
    public static Statement getStatementFrom(String statement) throws InvalidStatementException {
        final String s1 = statement.substring(0, statement.indexOf("("));
        final String[] s2 = getArguments(statement);
        final Value[] v = new Value[s2.length];
        for (int x = 0; x < v.length; x++) {
            if (!StringUtils.isEmpty(s2[x].trim())) {
                v[x] = new Value(s2[x].trim());
            }
        }
        try {
            return getMethod(s1, v);
        } catch (Exception ex) {
            throw new InvalidStatementException(ex.getMessage());
        }
    }

    /**
     * Gets a method from it's name and arguments.
     *
     * @param name name of the method
     * @param args arguments of the method
     * @return method from the name and argument
     * @throws Exception thrown when method does not exist
     */
    public static Method getMethod(String name, Value[] args) throws Exception {
        if (Keywords.contains(name)) {
            return Keywords.get(name, args);
        } else {
            return NativeMethods.getMethod(name, args);
        }
    }

    /**
     * Parses the string to return the name of the method.
     *
     * @param fullMethod full method with it's arguments
     * @return name of the method
     */
    public static String getMethodName(String fullMethod) {
        try {
            return fullMethod.substring(0, fullMethod.indexOf("("));
        } catch (StringIndexOutOfBoundsException ex) {
            return "Invalid Method";
        }
    }

    /**
     * Returns the arguments of the method. Ensures inner methods are intact.
     *
     * @param fullMethod full method string
     * @return arguments of the method
     */
    public static String[] getArguments(String fullMethod) {
        String innerFirstBrackets = fullMethod.substring(fullMethod.indexOf("(") + 1, fullMethod.lastIndexOf(')'));
        if (StringUtils.contains(innerFirstBrackets, "(") && StringUtils.contains(innerFirstBrackets, ")")) {
            Vector list = new Vector();
            int count = 0;
            int lastComma = 0;
            for (int x = 0; x < innerFirstBrackets.length(); x++) {
                if (innerFirstBrackets.charAt(x) == '(') {
                    count++;
                } else if (innerFirstBrackets.charAt(x) == ')') {
                    count--;
                }
                if (innerFirstBrackets.charAt(x) == ',' || x == innerFirstBrackets.length() - 1) {
                    if (count == 0) {
                        list.add(innerFirstBrackets.substring((lastComma == 0 ? -1 : lastComma) + 1,
                                (x == innerFirstBrackets.length() - 1 ? x + 1 : x)).trim());
                        lastComma = x;
                    }
                }
            }
            String[] items = new String[list.size()];
            for (int x = 0; x < items.length; x++) {
                items[x] = (String) list.get(x);
            }
            return items;
        } else {
            // No inner methods
            return StringUtils.split(innerFirstBrackets, ',');
        }
    }

    /**
     * Creates new method that performs the actions of the previous method, but
     * with new arguments.
     *
     * @param previous previous method
     * @param args arguments
     * @return method with new args
     */
    public static Method newMethod(final Method previous, final Value[] args) {
        return new Method(previous.getName(), args) {
            public void run(Value[] args) {
                previous.run(args);
            }
        };
    }
    private final String name;
    private final Value[] arguments;

    /**
     * Creates the method with it's name and arguments defined.
     *
     * @param name name of the method - cannot be modified
     * @param args arguments of the method - can be modified
     */
    public Method(String name, Value[] args) {
        super(name + "(" + ")");
        this.name = name;
        this.arguments = args;
    }

    /**
     * Creates the method with it's name defined.
     *
     * @param name name of the method - cannot be modified
     */
    public Method(String name) {
        super(name + "(" + ")");
        this.name = name;
        this.arguments = null;
    }

    public void run() {
        run(arguments);
    }

    /**
     * Returns the name of the method.
     *
     * @return method name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the arguments of the method in {@link Value} form.
     *
     * @return arguments of method
     */
    public Value[] getArguments() {
        return arguments;
    }

    /**
     * Runs the method.
     *
     * @param args arguments of the method.
     */
    public abstract void run(Value[] args);
}
