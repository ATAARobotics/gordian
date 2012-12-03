package edu.ata.script.storage;

import edu.ata.script.Data;
import edu.ata.script.StringUtils;
import edu.ata.script.data.ReturningMethod;

/**
 * @author Joel Gallant
 */
public class ReturningMethods extends Storage {

    /**
     *
     * @param data
     * @return
     */
    public static Data getMethodValue(String data) {
        String[] argsLiteral = StringUtils.split(data.substring(data.indexOf("(") + 1,
                data.lastIndexOf(')')), ',');
        Data[] args = new Data[argsLiteral.length];
        for (int x = 0; x < args.length; x++) {
            args[x] = Data.get(argsLiteral[x]);
        }
        return ((ReturningMethod) Data.RETURNING_METHODS.get(data.substring(0, data.indexOf("(")))).getValue(args);
    }

    /**
     *
     * @param key
     * @param value
     */
    protected void add(String key, Object value) {
        if (!(value instanceof ReturningMethod)) {
            throw new RuntimeException("Added new returning method to "
                    + "ReturningMethods that WAS NOT a ReturningMethod.");
        } else {
            getStorage().put(key, value);
        }
    }

    /**
     *
     * @param methodName
     * @param method
     */
    public void addMethod(String methodName, ReturningMethod method) {
        add(methodName, method);
    }
}
