package edu.ata.script.data;

import edu.ata.script.Data;
import edu.ata.script.data.booleans.Condition;

/**
 * @author Joel Gallant
 */
public class Boolean extends Data {

    public static boolean isType(java.lang.String data) {
        if (data.equalsIgnoreCase("true") || data.equalsIgnoreCase("false")) {
            return true;
        } else {
            return Condition.isType(data);
        }
    }

    public static Data get(java.lang.String data) {
        if (data.equalsIgnoreCase("true") || data.equalsIgnoreCase("false")) {
            return new Boolean(data);
        } else if (Condition.isType(data)) {
            return Condition.get(data);
        } else {
            throw new RuntimeException("Could not create boolean with - " + data);
        }
    }

    public Boolean(java.lang.String literalString) {
        super(literalString);
    }

    public Object getValue() {
        return java.lang.Boolean.valueOf(getLiteralString());
    }
}