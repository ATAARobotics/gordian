package org.reader.instruction;

import org.reader.Instruction;
import org.reader.Statement;
import org.reader.Value;
import org.reader.Variables;

/**
 * The declaration of variables.
 *
 * @author joel
 */
public class Declaration extends Instruction {

    /**
     * Hide this method to use the class. <i><b>This method is meant to be
     * hidden by it's subclasses. It is called in a tree-like structure down the
     * children of {@link Statement}.</i></b>
     *
     * @param statement the statement to analyze
     * @return whether it is a valid instruction
     */
    public static boolean isValid(String statement) {
        // Ensures one = sign
        return statement.contains("=") && statement.indexOf("=", statement.indexOf("=") + 1) < 0
                && statement.endsWith(";");
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
        return new Declaration(statement.substring(0, statement.indexOf("=")).trim(),
                new Value(statement.substring(statement.indexOf("=") + 1).replace(";", "").trim()));
    }
    private final String varName;
    private final Value value;

    /**
     * Creates the declaration by it's value and name.
     *
     * @param varName name of the variable
     * @param value value of the variable
     */
    public Declaration(String varName, Value value) {
        super(varName + " = " + value);
        this.varName = varName;
        this.value = value;
    }

    @Override
    public void run() {
        if (Variables.contains(varName)) {
            Variables.set(varName, value);
        } else {
            Variables.add(varName, value);
        }
    }
}
