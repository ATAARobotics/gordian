package edu.ata.script.instructions;

import edu.ata.script.Data;
import edu.ata.script.Instruction;
import edu.ata.script.data.NumberData;

/**
 * @author Joel Gallant
 */
public class Manipulation extends Instruction {

    public static boolean isType(String instruction) {
        return instruction.indexOf("++") >=0 || instruction.indexOf("--") >= 0;
    }

    public static Instruction get(String instruction) {
        return new Manipulation(instruction);
    }
    private final String instruction;

    public Manipulation(String instruction) {
        this.instruction = instruction;
    }

    public void run() {
        if (instruction.indexOf("++") >= 0) {
            Data.DATA_STORAGE.addData(instruction.substring(0, instruction.indexOf("++")),
                    Data.get((NumberData.doubleValue(instruction.substring(0,
                    instruction.indexOf("++"))) + 1) + ""));
        } else if (instruction.indexOf("--") >= 0) {
            Data.DATA_STORAGE.addData(instruction.substring(0, instruction.indexOf("--")),
                    Data.get((NumberData.doubleValue(instruction.substring(0,
                    instruction.indexOf("--"))) - 1) + ""));
        }
    }
}