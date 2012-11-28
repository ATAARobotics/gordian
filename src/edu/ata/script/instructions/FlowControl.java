package edu.ata.script.instructions;

import edu.ata.script.Instruction;
import edu.ata.script.instructions.flow.ConditionedFlow;
import edu.ata.script.instructions.flow.IntegerFlow;

/**
 * @author Joel Gallant
 */
public abstract class FlowControl extends Instruction {

    public static boolean isType(String instruction) {
        if (instruction.indexOf("{") < 0 || !instruction.endsWith("};")) {
            return false;
        }
        return ConditionedFlow.isType(instruction)
                || IntegerFlow.isType(instruction);
    }

    public static Instruction get(String instruction) {
        if (ConditionedFlow.isType(instruction)) {
            return ConditionedFlow.get(instruction);
        } else if (IntegerFlow.isType(instruction)) {
            return IntegerFlow.get(instruction);
        } else {
            throw new RuntimeException("Not flow control - " + instruction);
        }
    }
}
