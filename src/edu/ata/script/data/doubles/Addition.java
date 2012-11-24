package edu.ata.script.data.doubles;

import edu.ata.script.Data;
import edu.ata.script.data.Double;

/**
 * @author Joel Gallant
 */
public class Addition extends Calculation {

    public static boolean isType(java.lang.String data) {
    }

    public static Data get(java.lang.String data) {
    }
    
    public Addition(String literalString) {
        super(literalString, '+');
    }

    protected java.lang.Double doCalc(Double num1, Double num2) {
        return java.lang.Double.valueOf(num1.get() + num2.get());
    }
}