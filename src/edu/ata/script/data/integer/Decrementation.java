package edu.ata.script.data.integer;

/**
 * @author Joel Gallant
 */
public class Decrementation extends Manipulation {

    public static boolean isType(java.lang.String data) {
    }

    public static Data get(java.lang.String data) {
    }

    public Decrementation(String literalString) {
        super(literalString, "--");
    }

    protected Integer manipulate(Integer original) {
        return Integer.valueOf(original.intValue() - 1);
    }
}