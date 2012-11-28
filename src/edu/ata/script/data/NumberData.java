package edu.ata.script.data;

import edu.ata.script.Data;

/**
 * @author Joel Gallant
 */
public abstract class NumberData extends Data {

    public static int intValue(String data) {
        return ((NumberData) Data.get(data)).intValue();
    }

    public static double doubleValue(String data) {
        return ((NumberData) Data.get(data)).doubleValue();
    }

    public NumberData(String data) {
        super(data);
    }

    public abstract double doubleValue();

    public int intValue() {
        return Math.round((float) doubleValue());
    }
}