package edu.ata.script.data.integers;

import edu.ata.script.Data;
import edu.ata.script.StringUtils;

/**
 * @author Joel Gallant
 */
public class Incrementation extends Manipulation {

    public static boolean isType(java.lang.String data) {
        if (!StringUtils.contains(data, "++")) {
            return false;
        }
        if (Data.isType(data.substring(0, data.indexOf("++")))) {
            Data d = Data.get(data.substring(0, data.indexOf("++")));
            return (d instanceof edu.ata.script.data.Double
                    || d instanceof edu.ata.script.data.Integer);
        } else {
            return false;
        }
    }

    public static Data get(java.lang.String data) {
        return new Incrementation(data);
    }

    public Incrementation(String literalString) {
        super(literalString, "++");
    }

    protected Integer manipulate(Integer original) {
        return Integer.valueOf(original.intValue() + 1);
    }
}