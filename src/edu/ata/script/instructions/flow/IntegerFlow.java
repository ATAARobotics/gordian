package edu.ata.script.instructions.flow;

import edu.ata.script.Data;
import edu.ata.script.Instruction;
import edu.ata.script.Script;
import edu.ata.script.data.IntegerData;
import edu.ata.script.data.NumberData;
import edu.ata.script.instructions.FlowControl;

/**
 * @author Joel Gallant
 */
public abstract class IntegerFlow extends FlowControl {

    public static boolean isType(String instruction) {
        String flowStatement = instruction.substring(0, instruction.indexOf('{'));
        String condition = flowStatement.substring(flowStatement.indexOf("(") + 1,
                flowStatement.lastIndexOf(")"));
        return (Data.get(condition) instanceof IntegerData)
                && (ForStatement.isType(instruction));
    }

    public static Instruction get(String instruction) {
        if (ForStatement.isType(instruction)) {
            return ForStatement.get(instruction);
        } else {
            throw new RuntimeException("Invalid conditioned flow - " + instruction);
        }
    }
    private final String full;

    public IntegerFlow(String full) {
        this.full = full;
    }

    public void run() {
        String flowStatement = full.substring(0, full.indexOf('{'));
        String condition = flowStatement.substring(flowStatement.indexOf("(") + 1,
                flowStatement.lastIndexOf(")"));
        String instructions = full.substring(full.indexOf('{') + 1, full.lastIndexOf('}'));
        while(runAgain(NumberData.intValue(condition))) {
            new Script(instructions).run();
        }
    }

    protected abstract boolean runAgain(int argument);
}
