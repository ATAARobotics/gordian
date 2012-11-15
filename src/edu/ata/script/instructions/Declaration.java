package edu.ata.script.instructions;

import edu.ata.script.base.Instruction;
import edu.ata.script.base.InvalidStatementException;
import edu.ata.script.base.Statement;
import edu.ata.script.base.Value;
import edu.ata.script.base.Variables;
import edu.ata.script.blocks.IfStatement;
import edu.ata.script.blocks.WhileLoop;

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
        // Ensures first = sign is not == sign
        return statement.contains("=") && statement.endsWith(";") && statement.charAt(statement.indexOf("=")+1) != '='
                // Cannot be a conditional instruction
                && !IfStatement.isValid(statement) && !WhileLoop.isValid(statement);
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
