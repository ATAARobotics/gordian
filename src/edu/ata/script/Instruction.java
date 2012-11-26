package edu.ata.script;

import edu.ata.script.instructions.Declaration;
import edu.ata.script.instructions.FlowControl;
import edu.ata.script.instructions.Method;

/**
 * @author Joel Gallant
 */
public abstract class Instruction {

    public static boolean isType(String instruction) {
        instruction = instruction.trim();
        return FlowControl.isType(instruction)
                || Method.isType(instruction)
                || Declaration.isType(instruction);
    }

    public static Instruction get(String instruction) {
        instruction = instruction.trim();
        if (FlowControl.isType(instruction)) {
            return FlowControl.get(instruction);
        } else if (Method.isType(instruction)) {
            return Method.get(instruction);
        } else if (Declaration.isType(instruction)) {
            return Declaration.get(instruction);
        } else {
            throw new RuntimeException("Invalid instruction - " + instruction);
        }
    }

    public abstract void run();
}
