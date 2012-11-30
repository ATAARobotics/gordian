package edu.ata.script.data.doubles;

import edu.ata.script.Data;
import edu.ata.script.StringUtils;
import edu.ata.script.data.NumberData;

/**
 * @author Joel Gallant
 */
public class Addition extends Calculation {

    /**
     *
     * @param data
     * @return
     */
    public static boolean isType(String data) {
        if (!StringUtils.contains(data, "+")) {
            return false;
        }
        if (Data.isType(data.substring(0, data.indexOf('+')))
                && Data.isType(data.substring(data.indexOf('+') + 1))) {
            Data d1 = Data.get(data.substring(0, data.indexOf('+'))),
                    d2 = Data.get(data.substring(data.indexOf('+') + 1));
            return (d1 instanceof NumberData)
                    && (d2 instanceof NumberData);
        } else {
            return false;
        }
    }

    /**
     *
     * @param data
     * @return
     */
    public static Data get(String data) {
        return new Addition(data);
    }

    /**
     *
     * @param literalString
     */
    public Addition(String literalString) {
        super(literalString, '+');
    }

    /**
     *
     * @param num1
     * @param num2
     * @return
     */
    protected Double doCalc(double num1, double num2) {
        return Double.valueOf(num1 + num2);
    }
}