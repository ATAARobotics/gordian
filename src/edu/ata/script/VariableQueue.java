package edu.ata.script;

import java.util.Hashtable;

public class VariableQueue {

    private static final Hashtable HASHTABLE = new Hashtable();

    public static void put(String key, Object value) {
        HASHTABLE.put(key, value);
    }

    public static Object get(String key) {
        return HASHTABLE.get(key);
    }

    public static Hashtable getHASHTABLE() {
        return HASHTABLE;
    }
}
