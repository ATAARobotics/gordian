package edu.ata.script.data;

import edu.ata.script.Data;
import edu.ata.script.data.booleans.Condition;

/**
 * @author Joel Gallant
 */
public class BooleanData extends Data {

    public static boolean isType(String data) {
        if (data.equalsIgnoreCase("true") || data.equalsIgnoreCase("false")) {
            return true;
        } else {
            return Condition.isType(data);
        }
    }

    public static Data get(String data) {
        if (data.equalsIgnoreCase("true") || data.equalsIgnoreCase("false")) {
            return new BooleanData(data);
        } else if (Condition.isType(data)) {
            return Condition.get(data);
        } else {
            throw new RuntimeException("Could not create boolean with - " + data);
        }
    }

    public BooleanData(String literalString) {
        super(literalString);
    }

    public Object getValue() {
        return java.lang.Boolean.valueOf(getLiteralString().equalsIgnoreCase("true"));
    }
}