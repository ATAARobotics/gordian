package edu.ata.script;

import edu.ata.script.data.BooleanData;
import edu.ata.script.data.DoubleData;
import edu.ata.script.data.IntegerData;
import edu.ata.script.data.ReturningMethod;
import edu.ata.script.data.StringData;
import edu.ata.script.storage.DataStorage;
import edu.ata.script.storage.ReturningMethods;

/**
 * @author Joel Gallant
 */
public abstract class Data {

    public static final DataStorage DATA_STORAGE = new DataStorage();
    public static final ReturningMethods RETURNING_METHODS = new ReturningMethods();

    public static boolean isType(String data) {
        data = data.trim();
        return BooleanData.isType(data)
                || IntegerData.isType(data)
                || DoubleData.isType(data)
                || DATA_STORAGE.contains(data)
                || ReturningMethod.isType(data)
                || StringData.isType(data);
    }

    public static Data get(String data) {
        data = data.trim();
        /*:)*/ if (BooleanData.isType(data)) {
            return BooleanData.get(data);
        } else if (IntegerData.isType(data)) {
            return IntegerData.get(data);
        } else if (DoubleData.isType(data)) {
            return DoubleData.get(data);
        } else if (DATA_STORAGE.contains(data)) {
            return (Data) DATA_STORAGE.get(data);
        } else if (ReturningMethod.isType(data)) {
            return ReturningMethods.getMethodValue(data);
        } else if (StringData.isType(data)) {
            // Last option because everything is accepted.
            return StringData.get(data);
        } else {
            throw new RuntimeException("Could not parse data - " + data);
        }
    }
    private final String literalString;

    protected Data(String literalString) {
        literalString = literalString.trim();
        this.literalString = literalString;
    }

    public String getLiteralString() {
        return literalString;
    }

    public abstract Object getValue();
}