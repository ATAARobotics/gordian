package edu.ata.script.storage;

import edu.ata.script.Data;
import edu.ata.script.StringUtils;
import edu.ata.script.data.ReturningMethod;

/**
 * @author Joel Gallant
 */
public class ReturningMethods extends Storage {

    public static Data getMethodValue(String data) {
        return ((ReturningMethod) Data.RETURNING_METHODS.get(
                data.substring(0, data.indexOf("(")))).getValue(
                StringUtils.split(data.substring(data.indexOf("(") + 1,
                data.lastIndexOf(")")), ','));
    }

    protected void add(String key, Object value) {
        if (!(value instanceof ReturningMethod)) {
            throw new RuntimeException("Added new returning method to "
                    + "ReturningMethods that WAS NOT a ReturningMethod.");
        } else {
            getStorage().put(key, value);
        }
    }

    public void addMethod(String methodName, ReturningMethod method) {
        add(methodName, method);
    }
}
