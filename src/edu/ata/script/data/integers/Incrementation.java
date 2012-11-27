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
            return (d instanceof edu.ata.script.data.Integer)
                    || (d instanceof edu.ata.script.data.Double);
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

    protected Integer manipulate(int original) {
        if (Data.DATA_STORAGE.contains(getLiteralString().substring(0, getLiteralString().indexOf("++")))) {
            Data.DATA_STORAGE.addData(getLiteralString().substring(0, getLiteralString().indexOf("++")),
                    edu.ata.script.data.Integer.get((original + 1) + ""));
        }
        return Integer.valueOf(original + 1);
    }
}