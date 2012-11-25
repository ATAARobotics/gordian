package edu.ata.script.data.strings;

import edu.ata.script.Data;
import edu.ata.script.StringUtils;

/**
 * @author Joel Gallant
 */
public class Concatenation extends edu.ata.script.data.String {

    public static boolean isType(java.lang.String data) {
        // Always check Calculation (specifically addition) before checking this.
        return StringUtils.contains(data, "+");
    }

    public static Data get(java.lang.String data) {
        return new Concatenation(data);
    }
    
    public Concatenation(String literalString) {
        super(literalString);
    }

    public Object getValue() {
        String value = "";
        String[] parts = StringUtils.split(getLiteralString(), '+');
        for(int x = 0; x < parts.length; x++) {
            value += Data.get(parts[x]).getValue();
        }
        return value;
    }
}