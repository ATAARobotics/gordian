package edu.ata.script.data;

import edu.ata.script.Data;
import edu.ata.script.StringUtils;
import edu.ata.script.data.strings.Concatenation;

/**
 * @author Joel Gallant
 */
public class String extends Data {

    public static boolean isType(java.lang.String data) {
        // Should be last resort (test all before this)
        return true;
    }

    public static Data get(java.lang.String data) {
        if(Concatenation.isType(data)) {
            return Concatenation.get(data);
        } else {
            return new String(data);
        }
    }
    
    private final java.lang.String interpretedString;
    
    public String(java.lang.String literalString) {
        super(literalString);
        this.interpretedString = StringUtils.replace(literalString.trim(), '\"', "");
    }

    public Object getValue() {
        return interpretedString;
    }
}