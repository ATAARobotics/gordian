package edu.gordian.special;

import edu.gordian.Instruction;
import java.util.ArrayList;

public final class For implements Special {

    private final int loops;
    private final ArrayList list = new ArrayList();

    public For(int loops) {
        this.loops = loops;
    }

    public void add(Instruction instruction) {
        list.add(instruction);
    }

    public void run() {
        for (int x = 0; x < loops; x++) {
            for (int i = 0; i < list.size(); i++) {
                ((Instruction) list.get(i)).run();
            }
        }
    }
}
