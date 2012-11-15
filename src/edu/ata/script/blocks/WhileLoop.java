package edu.ata.script.blocks;

import edu.ata.script.Instruction;
import edu.ata.script.InvalidStatementException;
import edu.ata.script.ScriptReader;
import edu.ata.script.Statement;
import edu.ata.script.StringUtils;
import edu.ata.script.values.Condition;

/**
 * WHILE loop. Self-explanatory.
 *
 * @author joel
 */
public class WhileLoop extends Instruction {

    /**
     * Hide this method to use the class. <i><b>This method is meant to be
     * hidden by it's subclasses. It is called in a tree-like structure down the
     * children of {@link Statement}.</i></b>
     *
     * @param statement the statement to analyze
     * @return whether it is a valid instruction
     */
    public static boolean isValid(String statement) {
        statement = StringUtils.replace(statement, '{', "").trim();
        return statement.startsWith("WHILE(") && statement.endsWith(")");
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
        return new WhileLoop(statement.substring(statement.indexOf("(") + 1, statement.indexOf(")")));
    }
    /**
     * Condition of the while loop. Is checked each loop.
     */
    public final String evaluation;

    /**
     * Creates the while loop with a condition.
     *
     * @param evaluation condition of the while loop
     */
    public WhileLoop(String evaluation) {
        super("WHILE(" + evaluation + ")");
        this.evaluation = evaluation;
    }

    public void run() {
        this.linesToSkip = linesUntilBreak(fullScript, line);
        String[] instructions = StringUtils.split(fullScript, ScriptReader.LINE_SEPARATOR);
        String whileLoop = "";
        int currentLine = line;
        for (int x = 0; x < linesUntilBreak(fullScript, line); x++) {
            whileLoop += instructions[++currentLine] + System.getProperty("line.separator");
        }
        Condition condition = Condition.getConditionFrom(evaluation);
        while (condition.isTrue()) {
            new ScriptReader(whileLoop).runFullScript();
        }
    }
}