package edu.ata.script.instructions.flow;

import edu.ata.script.Data;
import edu.ata.script.Instruction;

/**
 * @author Joel Gallant
 */
public class WhileStatement extends ConditionedFlow {

    public static boolean isType(String instruction) {
        return instruction.substring(1).trim().startsWith("while(");
    }

    public static Instruction get(String instruction) {
        return new WhileStatement(instruction);
    }

    public WhileStatement(String full) {
        super(full);
    }

    protected boolean runAgain(String args) {
        Data arg = Data.get(args);
        if (arg instanceof edu.ata.script.data.Boolean) {
            if (((edu.ata.script.data.Boolean) arg).getValue().equals(Boolean.TRUE)) {
                return true;
            }
        }
        return false;
    }
}
