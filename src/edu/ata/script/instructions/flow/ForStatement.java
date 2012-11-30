package edu.ata.script.instructions.flow;

import edu.ata.script.Instruction;

/**
 * @author Joel Gallant
 */
public class ForStatement extends IntegerFlow {

    /**
     *
     * @param instruction
     * @return
     */
    public static boolean isType(String instruction) {
        return instruction.trim().startsWith("for(");
    }

    /**
     *
     * @param instruction
     * @return
     */
    public static Instruction get(String instruction) {
        return new ForStatement(instruction);
    }

    private int count = 0;

    /**
     *
     * @param full
     */
    public ForStatement(String full) {
        super(full);
    }

    /**
     *
     * @param argument
     * @return
     */
    protected boolean runAgain(int argument) {
        return ++count <= argument;
    }
}
