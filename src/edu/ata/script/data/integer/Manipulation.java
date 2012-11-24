package edu.ata.script.data.integer;

import edu.ata.script.Data;

/**
 * @author Joel Gallant
 */
public abstract class Manipulation extends edu.ata.script.data.Integer {

    public static boolean isType(java.lang.String data) {
    }

    public static Data get(java.lang.String data) {
    }
    private final String manipulation;

    public Manipulation(String literalString, String manipulation) {
        super(literalString);
        this.manipulation = manipulation;
    }

    protected Object getValue() {
        return manipulate(Integer.valueOf(getLiteralString().substring(0,
                getLiteralString().indexOf(manipulation))));
    }

    protected abstract Integer manipulate(Integer original);
}