package edu.ata.script.instructions;

import edu.ata.script.Data;
import edu.ata.script.Instruction;
import edu.ata.script.StringUtils;
import edu.ata.script.storage.Methods;

/**
 * @author Joel Gallant
 */
public class Method extends Instruction {

    public static boolean isType(String instruction) {
        if(!StringUtils.contains(instruction, "(") || !StringUtils.contains(instruction, ")")) {
            return false;
        }
        return Methods.METHODS_STORAGE.contains(instruction.substring(0, instruction.indexOf('(')));
    }

    public static Instruction get(String instruction) {
        return new Method(instruction);
    }
    private final String method;
    private final String[] args;

    public Method(String method) {
        this.method = method.substring(0, method.indexOf('('));
        this.args = StringUtils.split(method.substring(method.indexOf('(') + 1,
                method.lastIndexOf(')')), ',');
    }

    public void run() {
        Data[] arguments = new Data[this.args.length];
        for(int x = 0; x < arguments.length; x++) {
            arguments[x] = Data.get(args[x]);
        }
        ((MethodBody)Methods.METHODS_STORAGE.get(method)).run(arguments);
    }
}
