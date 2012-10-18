package org.reader;

import org.reader.blocks.FOR;
import org.reader.blocks.IF;
import org.reader.blocks.WHILE;
import org.reader.instruction.Declaration;
import org.reader.instruction.Method;

/**
 * Basic statement that does something when used.
 *
 * @author joel
 */
public abstract class Instruction extends Statement implements Runnable {

    /**
     * Hide this method to use the class. <i><b>This method is meant to be
     * hidden by it's subclasses. It is called in a tree-like structure down the
     * children of {@link Statement}.</i></b>
     *
     * @param statement the statement to analyze
     * @return whether it is a valid instruction
     */
    public static boolean isValid(String statement) {
        return statement.endsWith(";") || statement.endsWith("{") || statement.equals("}");
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
        if (Method.isValid(statement)) {
            return Method.getStatementFrom(statement);
        } else if (Declaration.isValid(statement)) {
            return Declaration.getStatementFrom(statement);
        } else if (IF.isValid(statement)) {
            return IF.getStatementFrom(statement);
        } else if (FOR.isValid(statement)) {
            return FOR.getStatementFrom(statement);
        } else if (WHILE.isValid(statement)) {
            return WHILE.getStatementFrom(statement);
        } else if (statement.equals("}")) {
            // Does nothing
            return new Statement();
        } else {
            throw new InvalidStatementException(statement + " is not a statement type.");
        }
    }
    private final String instruction;

    /**
     * Creates instruction by a string. The instruction is held in
     * {@code instruction}.
     *
     * @param instruction full code snippet
     */
    public Instruction(String instruction) {
        this.instruction = instruction;
    }

    /**
     * Returns the actual instruction.
     *
     * @return the literal instruction
     */
    public String getInstruction() {
        return instruction;
    }
}
