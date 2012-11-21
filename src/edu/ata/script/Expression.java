package edu.ata.script;

import java.util.Vector;

public class Expression extends Instruction {

    public static boolean isExpression(String instruction) {
        if (instruction.contains("{") && instruction.contains("}")) {
            return true;
        }
        return false;
    }
    private final Vector instructions = new Vector();

    public Expression(String full) {
        super(full.substring(full.indexOf("{") + 1, full.indexOf("};")));
        full = StringUtils.replace(full, '\n', ";");
        InstructionIterator iterator = new InstructionIterator(full);
        while (iterator.hasNext()) {
            try {
                instructions.add(iterator.getNext());
            } catch (NullPointerException ex) {
                System.err.println("Error! - Script contains non-instruction\n    "
                        + ex.getMessage());
                break;
            }
        }
    }

    public String getLiteral() {
        // Add three spaces to account for '};'
        return super.getLiteral() + "  ";
    }

    public void run() {
        for (int x = 0; x < instructions.size(); x++) {
            ((Instruction) instructions.elementAt(x)).run();
        }
    }
}
