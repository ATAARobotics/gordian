package org.reader.blocks;

import org.reader.Instruction;
import org.reader.Statement;
import org.reader.Value;
import org.reader.values.Condition;

/**
 * IF statement. Self-explanatory.
 *
 * @author joel
 */
public class IF extends Instruction {

    /**
     * Hide this method to use the class. <i><b>This method is meant to be
     * hidden by it's subclasses. It is called in a tree-like structure down the
     * children of {@link Statement}.</i></b>
     *
     * @param statement the statement to analyze
     * @return whether it is a valid instruction
     */
    public static boolean isValid(String statement) {
        statement = statement.replace("{", "").trim();
        return statement.startsWith("IF(") && statement.endsWith(")");
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
        return new IF(Condition.getConditionFrom(new Value(statement.substring(statement.indexOf("(") + 1, statement.indexOf(")")))));
    }
    /**
     * The condition of the IF statement.
     */
    public final Condition condition;

    /**
     * Creates statement with the condition.
     *
     * @param condition condition of the if statement
     */
    public IF(Condition condition) {
        super("IF(" + condition.v1 + "vs" + condition.v2 + ")");
        this.condition = condition;
    }

    @Override
    public void run() {
        if (condition.isTrue()) {
            this.linesToSkip = 1;
        } else {
            this.linesToSkip = linesUntilBreak(fullScript, line);
        }

    }
}
