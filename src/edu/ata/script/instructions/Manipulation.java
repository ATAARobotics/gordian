package edu.ata.script.instructions;

import edu.ata.script.Instruction;

/**
 * @author Joel Gallant
 */
public class Manipulation extends Instruction {

    public static boolean isType(String instruction) {
        return instruction.indexOf("++") >= 0 || instruction.indexOf("--") >= 0;
    }

    public static Instruction get(String instruction) {
        return new Manipulation(instruction);
    }
    private final Declaration declaration;

    public Manipulation(String instruction) {
        if (instruction.indexOf("++") >= 0) {
            String name = instruction.substring(0, instruction.indexOf("++"));
            instruction = name + "=" + name + "+1";
        } else if (instruction.indexOf("--") >= 0) {
            String name = instruction.substring(0, instruction.indexOf("--"));
            instruction = name + "=" + name + "-1";
        }
        this.declaration = (Declaration) Declaration.get(instruction);
    }

    public void run() {
        declaration.run();
    }
}