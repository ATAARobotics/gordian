package edu.ata.script.instructions.flow;

import edu.ata.script.Data;
import edu.ata.script.Instruction;
import edu.ata.script.data.BooleanData;

/**
 * @author Joel Gallant
 */
public class IfStatement extends ConditionedFlow {

    public static boolean isType(String instruction) {
        return instruction.trim().startsWith("if(");
    }

    public static Instruction get(String instruction) {
        return new IfStatement(instruction);
    }
    private boolean ran = false;

    public IfStatement(String full) {
        super(full);
    }

    protected boolean runAgain(String args) {
        Data arg = Data.get(args);
        if (arg instanceof BooleanData) {
            if (((BooleanData) arg).getValue().equals(Boolean.TRUE)) {
                if (!ran) {
                    ran = true;
                    return true;
                }
            }
        }
        return false;
    }
}
