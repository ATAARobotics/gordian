package edu.gordian.special;

import edu.gordian.Gordian;
import edu.gordian.Instruction;
import java.util.ArrayList;

public final class While implements Special {

    private final Gordian gordian;
    private final String literal;
    private final ArrayList list = new ArrayList();

    public While(Gordian gordian, String literal) {
        this.gordian = gordian;
        this.literal = literal;
    }

    public void add(Instruction instruction) {
        list.add(instruction);
    }

    public void run() {
        while (gordian.convertVariable(literal).getValue().equals(Boolean.TRUE)) {
            for (int x = 0; x < list.size(); x++) {
                ((Instruction) list.get(x)).run();
            }
        }
    }
}
