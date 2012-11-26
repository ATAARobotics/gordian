package edu.ata.script.instructions.flow;

import edu.ata.script.Block;
import edu.ata.script.Data;
import edu.ata.script.Instruction;
import edu.ata.script.instructions.FlowControl;

/**
 * @author Joel Gallant
 */
public abstract class IntegerFlow extends FlowControl {

    public static boolean isType(String instruction) {
        String flowStatement = instruction.substring(1, instruction.indexOf(';'));
        String condition = flowStatement.substring(flowStatement.indexOf("(") + 1,
                flowStatement.lastIndexOf(")"));
        return (Data.get(condition) instanceof edu.ata.script.data.Integer)
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
        String flowStatement = full.substring(1, full.indexOf(';'));
        String condition = flowStatement.substring(flowStatement.indexOf("(") + 1,
                flowStatement.lastIndexOf(")"));
        while(runAgain(((edu.ata.script.data.Integer)Data.get(condition)).get())) {
            new Block(full.substring(full.indexOf(';') + 1, full.lastIndexOf('$'))).run();
        }
    }

    protected abstract boolean runAgain(int argument);
}
