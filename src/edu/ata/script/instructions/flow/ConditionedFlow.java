package edu.ata.script.instructions.flow;

import edu.ata.script.Data;
import edu.ata.script.Instruction;
import edu.ata.script.Script;
import edu.ata.script.data.BooleanData;
import edu.ata.script.instructions.FlowControl;

/**
 * @author Joel Gallant
 */
public abstract class ConditionedFlow extends FlowControl {

    public static boolean isType(String instruction) {
        String flowStatement = instruction.substring(0, instruction.indexOf('{'));
        String condition = flowStatement.substring(flowStatement.indexOf("(") + 1,
                flowStatement.lastIndexOf(")"));
        return (Data.get(condition) instanceof BooleanData)
                && (IfStatement.isType(instruction)
                || WhileStatement.isType(instruction));
    }

    public static Instruction get(String instruction) {
        if (IfStatement.isType(instruction)) {
            return IfStatement.get(instruction);
        } else if (WhileStatement.isType(instruction)) {
            return WhileStatement.get(instruction);
        } else {
            throw new RuntimeException("Invalid conditioned flow - " + instruction);
        }
    }
    private final String full;

    public ConditionedFlow(String full) {
        this.full = full;
    }

    protected abstract boolean runAgain(String args);

    public void run() {
        String flowStatement = full.substring(0, full.indexOf('{'));
        String condition = flowStatement.substring(flowStatement.indexOf("(") + 1,
                flowStatement.lastIndexOf(")"));
        while (runAgain(condition)) {
            new Script(full.substring(full.indexOf('{') + 1, full.lastIndexOf('}'))).run();
        }
    }
}
