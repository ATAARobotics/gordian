package edu.gordian.special;

import edu.gordian.Gordian;
import edu.gordian.Instruction;
import java.util.ArrayList;

public final class If implements Special {

    private final Gordian gordian;
    private final String literal;
    private final ArrayList list = new ArrayList();
    private boolean ran = false;

    public If(Gordian gordian, String literal) {
        this.gordian = gordian;
        this.literal = literal;
    }

    public void add(Instruction instruction) {
        list.add(instruction);
    }

    public boolean ran() {
        return ran;
    }

    public void run() {
        if (gordian.convertVariable(literal).getValue().equals(Boolean.TRUE)) {
            ran = true;
            for (int x = 0; x < list.size(); x++) {
                ((Instruction) list.get(x)).run();
            }
        } else {
            ran = false;
        }
    }
}
