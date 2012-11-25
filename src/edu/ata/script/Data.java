package edu.ata.script;

import edu.ata.script.storage.DataStorage;

/**
 * @author Joel Gallant
 */
public abstract class Data {

    public static final DataStorage DATA_STORAGE = new DataStorage();

    public static boolean isType(String data) {
        data = data.trim();
        return edu.ata.script.data.Boolean.isType(data)
                || edu.ata.script.data.Integer.isType(data)
                || edu.ata.script.data.Double.isType(data)
                || edu.ata.script.data.String.isType(data)
                || DATA_STORAGE.contains(data);
    }

    public static Data get(String data) {
        data = data.trim();
        /*:)*/ if (edu.ata.script.data.Boolean.isType(data)) {
            return edu.ata.script.data.Boolean.get(data);
        } else if (edu.ata.script.data.Integer.isType(data)) {
            return edu.ata.script.data.Integer.get(data);
        } else if (edu.ata.script.data.Double.isType(data)) {
            return edu.ata.script.data.Double.get(data);
        } else if (edu.ata.script.data.String.isType(data)) {
            return edu.ata.script.data.String.get(data);
        } else if (DATA_STORAGE.contains(data)) {
            return (Data) DATA_STORAGE.get(data);
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