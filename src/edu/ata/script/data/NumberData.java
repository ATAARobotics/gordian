package edu.ata.script.data;

import edu.ata.script.Data;

/**
 * @author Joel Gallant
 */
public abstract class NumberData extends Data {

    /**
     *
     * @param data
     * @return
     */
    public static int intValue(String data) {
        return ((NumberData) Data.get(data)).intValue();
    }

    /**
     *
     * @param data
     * @return
     */
    public static double doubleValue(String data) {
        return ((NumberData) Data.get(data)).doubleValue();
    }

    /**
     *
     * @param data
     */
    public NumberData(String data) {
        super(data);
    }

    /**
     *
     * @return
     */
    public abstract double doubleValue();

    /**
     *
     * @return
     */
    public int intValue() {
        return (int) doubleValue();
    }
}