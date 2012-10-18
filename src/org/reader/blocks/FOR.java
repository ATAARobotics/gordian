package org.reader.blocks;

import org.reader.Instruction;
import org.reader.ScriptReader;
import org.reader.Statement;
import org.reader.Value;

/**
 * FOR loop class. Pretty self-explanatory.
 *
 * @author joel
 */
public class FOR extends Instruction {

    /**
     * Hide this method to use the class. <i><b>This method is meant to be
     * hidden by it's subclasses. It is called in a tree-like structure down the
     * children of {@link Statement}.</i></b>
     *
     * @param statement the statement to analyze
     * @return whether it is a valid instruction
     */
    public static boolean isValid(String statement) {
        if (!(statement.contains("(") && statement.contains(")"))) {
            return false;
        }
        return statement.startsWith("FOR(") && (statement.endsWith(")") || statement.endsWith("{"));
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
        return new FOR(new Value(statement.substring(statement.indexOf("(") + 1, statement.indexOf(")"))));
    }
    /**
     * The amount of times to repeat the loop.
     */
    public final int repititions;

    /**
     * Creates a for loop based on one value. Has to be an integer.
     *
     * @param value amount of times to loop
     */
    public FOR(Value value) {
        this(Integer.parseInt(value.getValue().toString()));
    }

    /**
     * Creates a for loop that loops a certain amount of time.
     *
     * @param times amount of times to loop
     */
    public FOR(int times) {
        super("FOR(" + ")");
        this.repititions = times;
    }

    @Override
    public void run() {
        this.linesToSkip = linesUntilBreak(fullScript, line);
        String[] instructions = fullScript.split(System.getProperty("line.separator"));
        String forLoop = "";
        int currentLine = line;
        for (int x = 0; x < linesUntilBreak(fullScript, line); x++) {
            forLoop += instructions[++currentLine] + System.getProperty("line.separator");
        }
        for (int x = 0; x < repititions; x++) {
            ScriptReader.runScript(forLoop);
        }
    }
}
