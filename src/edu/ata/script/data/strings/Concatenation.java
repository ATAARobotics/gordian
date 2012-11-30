package edu.ata.script.data.strings;

import edu.ata.script.Data;
import edu.ata.script.StringUtils;
import edu.ata.script.data.StringData;

/**
 * @author Joel Gallant
 */
public class Concatenation extends StringData {

    /**
     *
     * @param data
     * @return
     */
    public static boolean isType(String data) {
        // Always check Calculation (specifically addition) before checking this.
        return StringUtils.contains(data, "+");
    }

    /**
     *
     * @param data
     * @return
     */
    public static Data get(String data) {
        return new Concatenation(data);
    }
    
    /**
     *
     * @param literalString
     */
    public Concatenation(String literalString) {
        super(literalString);
    }

    /**
     *
     * @return
     */
    public Object getValue() {
        String value = "";
        String[] parts = StringUtils.split(getLiteralString(), '+');
        for(int x = 0; x < parts.length; x++) {
            value += Data.get(parts[x]).getValue();
        }
        return value;
    }
}