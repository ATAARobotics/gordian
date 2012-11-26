package edu.ata.script.storage;

import edu.ata.script.Data;

/**
 * @author Joel Gallant
 */
public class DataStorage extends Storage {

    // Important - do not add classes to this package unless you are okay with
    // them accessing this method.
    protected void add(String key, Object data) throws IllegalArgumentException {
        if (!(data instanceof Data)) {
            throw new IllegalArgumentException("Added data to the storage that "
                    + "WAS NOT DATA.");
        } else {
            getStorage().put(key, data);
        }
    }

    public void addData(String key, Data data) {
        // Stores the data statically
        add(key, Data.get(data.getValue().toString()));
    }
}