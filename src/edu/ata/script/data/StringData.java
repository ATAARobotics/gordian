package edu.ata.script.data;

import edu.ata.script.Data;
import edu.ata.script.StringUtils;
import edu.ata.script.data.strings.Concatenation;

/**
 * @author Joel Gallant
 */
public class StringData extends Data {

    /**
     *
     * @param data
     * @return
     */
    public static boolean isType(String data) {
        // Should be last resort (test all before this)
        return true;
    }

    /**
     *
     * @param data
     * @return
     */
    public static Data get(String data) {
        if(Concatenation.isType(data)) {
            return Concatenation.get(data);
        } else {
            return new StringData(data);
        }
    }
    
    private final String interpretedString;
    
    /**
     *
     * @param literalString
     */
    public StringData(String literalString) {
        super(literalString);
        this.interpretedString = StringUtils.replace(literalString.trim(), '\"', "");
    }

    /**
     *
     * @return
     */
    public Object getValue() {
        return interpretedString;
    }
}