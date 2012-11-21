package edu.ata.script;

import java.util.Vector;

public final class Script extends Instruction {

    private final Vector instructions = new Vector();

    public Script(String full) {
        super(full);
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

    public void run() {
        VariableQueue.getHASHTABLE().clear();
        for (int x = 0; x < instructions.size(); x++) {
            ((Instruction) instructions.elementAt(x)).run();
        }
    }
}
