package edu.ata.script;

import java.util.Hashtable;

public class VariableQueue {

    private static final Hashtable HASHTABLE = new Hashtable();

    public static void put(String key, Data value) {
        HASHTABLE.put(key.trim(), value);
    }

    public static Data get(String key) {
        return (Data) HASHTABLE.get(key.trim());
    }

    public static boolean containsKey(String key) {
        return HASHTABLE.containsKey(key.trim());
    }
    
    public static Hashtable getHASHTABLE() {
        return HASHTABLE;
    }
}
