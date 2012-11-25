package edu.ata.script.data.doubles;

import edu.ata.script.Data;
import edu.ata.script.StringUtils;
import edu.ata.script.data.Double;

/**
 * @author Joel Gallant
 */
public class Division extends Calculation {

    public static boolean isType(java.lang.String data) {
        if(!StringUtils.contains(data, "/")) {
            return false;
        }
        if (Data.isType(data.substring(0, data.indexOf('/')))
                && Data.isType(data.substring(data.indexOf('/') + 1))) {
            Data d1 = Data.get(data.substring(0, data.indexOf('/'))),
                    d2 = Data.get(data.substring(data.indexOf('/') + 1));
            return (d1 instanceof edu.ata.script.data.Double || 
                    d1 instanceof edu.ata.script.data.Integer) &&
                    (d2 instanceof edu.ata.script.data.Double ||
                    d2 instanceof edu.ata.script.data.Integer);
        } else {
            return false;
        }
    }

    public static Data get(java.lang.String data) {
        return new Division(data);
    }

    public Division(String literalString) {
        super(literalString, '/');
    }

    protected java.lang.Double doCalc(Double num1, Double num2) {
        return java.lang.Double.valueOf(num1.get() / num2.get());
    }
}