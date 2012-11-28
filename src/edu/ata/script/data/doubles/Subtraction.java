package edu.ata.script.data.doubles;

import edu.ata.script.Data;
import edu.ata.script.data.NumberData;

/**
 * @author Joel Gallant
 */
public class Subtraction extends Calculation {

    public static boolean isType(String data) {
        if (data.indexOf('-') == 0) {
            data = data.substring(1);
        }
        if (data.indexOf('-') < 0) {
            return false;
        }
        if (Data.isType(data.substring(0, data.indexOf('-')))
                && Data.isType(data.substring(data.indexOf('-') + 1))) {
            Data d1 = Data.get(data.substring(0, data.indexOf('-'))),
                    d2 = Data.get(data.substring(data.indexOf('-') + 1));
            return (d1 instanceof NumberData)
                    && (d2 instanceof NumberData);
        } else {
            return false;
        }
    }

    public static Data get(String data) {
        return new Subtraction(data);
    }

    public Subtraction(String literalString) {
        super(literalString, '-');
    }

    protected Double doCalc(double num1, double num2) {
        return Double.valueOf(num1 - num2);
    }
}