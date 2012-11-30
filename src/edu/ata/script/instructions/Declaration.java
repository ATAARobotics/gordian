package edu.ata.script.instructions;

import edu.ata.script.Data;
import edu.ata.script.Instruction;
import edu.ata.script.StringUtils;

/**
 * @author Joel Gallant
 */
public class Declaration extends Instruction {

    /**
     *
     * @param instruction
     * @return
     */
    public static boolean isType(String instruction) {
        return StringUtils.contains(instruction, "=");
    }

    /**
     *
     * @param instruction
     * @return
     */
    public static Instruction get(String instruction) {
        return new Declaration(instruction);
    }
    private final String instruction;

    /**
     *
     * @param instruction
     */
    public Declaration(String instruction) {
        this.instruction = instruction;
    }

    /**
     *
     */
    public void run() {
        Data.DATA_STORAGE.addData(instruction.substring(0, instruction.indexOf('=')).trim(),
                Data.get(instruction.substring(instruction.indexOf('=') + 1).trim()));
    }
}
