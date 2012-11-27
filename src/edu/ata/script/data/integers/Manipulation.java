package edu.ata.script.data.integers;

import edu.ata.script.Data;

/**
 * @author Joel Gallant
 */
public abstract class Manipulation extends edu.ata.script.data.Integer {

    public static boolean isType(java.lang.String data) {
        return Incrementation.isType(data)
                || Decrementation.isType(data);
    }

    public static Data get(java.lang.String data) {
        if (Incrementation.isType(data)) {
            return Incrementation.get(data);
        } else if (Decrementation.isType(data)) {
            return Decrementation.get(data);
        } else {
            throw new RuntimeException("Tried to get manipulation that was not "
                    + "a manipulation - " + data);
        }
    }
    private final String manipulation;

    public Manipulation(String literalString, String manipulation) {
        super(literalString);
        this.manipulation = manipulation;
    }

    public Object getValue() {
        return manipulate(((edu.ata.script.data.Integer)Data.get(getLiteralString().substring(0,
                getLiteralString().indexOf(manipulation)))).get());
    }

    protected abstract Integer manipulate(int original);
}