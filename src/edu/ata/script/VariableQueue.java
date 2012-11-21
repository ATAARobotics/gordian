package edu.ata.script;

import java.util.Hashtable;

public class VariableQueue {

    private static final Hashtable HASHTABLE = new Hashtable();

    public static void put(String key, Data value) {
        HASHTABLE.put(key, value);
    }

    public static Data get(String key) {
        return (Data) HASHTABLE.get(key);
    }

    public static boolean containsKey(String key) {
        return HASHTABLE.containsKey(key);
    }
    
    public static Hashtable getHASHTABLE() {
        return HASHTABLE;
    }
}
