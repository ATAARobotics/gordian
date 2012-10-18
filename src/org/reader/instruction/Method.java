package org.reader.instruction;

import org.reader.Instruction;
import org.reader.Statement;
import org.reader.Value;
import org.reader.instruction.methods.Keywords;
import org.reader.instruction.methods.NativeMethods;

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
     * @throws org.reader.Statement.InvalidStatementException
     */
    public static Statement getStatementFrom(String statement) throws InvalidStatementException {
        final String s1 = statement.substring(0, statement.indexOf("("));
        final String[] s2 = statement.substring(statement.indexOf("(") + 1, statement.lastIndexOf(")")).split(",");
        final Value[] v = new Value[s2.length];
        for (int x = 0; x < v.length; x++) {
            if (!s2[x].trim().isEmpty()) {
                v[x] = new Value(s2[x].trim());
            }
        }
        try {
            return getMethod(s1, v);
        } catch (NoSuchFieldException ex) {
            throw new InvalidStatementException(ex.getMessage());
        }
    }

    /**
     * Gets a method from it's name and arguments.
     *
     * @param name name of the method
     * @param args arguments of the method
     * @return method from the name and argument
     * @throws NoSuchFieldException thrown when no method exists
     */
    public static Method getMethod(String name, Value[] args) throws NoSuchFieldException {
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
     * Name of the method.
     */
    public final String name;
    /**
     * Arguments of the method.
     */
    public Value[] arguments;

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

    @Override
    public void run() {
        run(arguments);
    }

    /**
     * Runs the method.
     *
     * @param args arguments of the method.
     */
    public abstract void run(Value[] args);
}
