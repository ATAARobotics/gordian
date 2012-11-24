package edu.ata.script.data.integer;

import edu.ata.script.Data;

/**
 * @author Joel Gallant
 */
public class Incrementation extends Manipulation {

    public static boolean isType(java.lang.String data) {
    }

    public static Data get(java.lang.String data) {
    }

    public Incrementation(String literalString) {
        super(literalString, "++");
    }

    protected Integer manipulate(Integer original) {
        return Integer.valueOf(original.intValue() + 1);
    }
}