package edu.ata.script.instructions.flow;

import edu.ata.script.Instruction;

/**
 * @author Joel Gallant
 */
public class ForStatement extends IntegerFlow {

    public static boolean isType(String instruction) {
        return instruction.substring(1).trim().startsWith("for(");
    }

    public static Instruction get(String instruction) {
        return new ForStatement(instruction);
    }

    private int count = 0;

    public ForStatement(String full) {
        super(full);
    }

    protected boolean runAgain(int argument) {
        return ++count <= argument;
    }
}
