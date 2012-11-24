package edu.ata.script.data.doubles;

import edu.ata.script.Data;
import edu.ata.script.StringUtils;
import edu.ata.script.data.Double;

/**
 * @author Joel Gallant
 */
public class Subtraction extends Calculation {

    public static boolean isType(java.lang.String data) {
        if(!StringUtils.contains(data, "-")) {
            return false;
        }
        // Cannot ensure that it is a number
        return Data.isType(data.substring(0, data.indexOf('-')))
                && Data.isType(data.substring(data.indexOf('-') + 1));
    }

    public static Data get(java.lang.String data) {
        return new Subtraction(data);
    }
    
    public Subtraction(String literalString) {
        super(literalString, '-');
    }

    protected java.lang.Double doCalc(Double num1, Double num2) {
        return java.lang.Double.valueOf(num1.get() - num2.get());
    }
}