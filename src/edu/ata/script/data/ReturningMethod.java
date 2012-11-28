package edu.ata.script.data;

import edu.ata.script.Data;
import edu.ata.script.StringUtils;
import edu.ata.script.storage.ReturningMethods;

/**
 * @author Joel Gallant
 */
public abstract class ReturningMethod {

    public static boolean isType(String data) {
        if(!StringUtils.contains(data, "(") || !StringUtils.contains(data, ")")) {
            return false;
        }
        return Data.RETURNING_METHODS.contains(data.substring(0, data.indexOf("(")));
    }

    public abstract Data getValue(String[] args);
}
