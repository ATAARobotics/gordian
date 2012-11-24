package edu.ata.script.storage;

import java.util.Hashtable;

/**
 * @author Joel Gallant
 */
public abstract class Storage {

    private final Hashtable storage = new Hashtable();
    
    protected Hashtable getStorage() {
        return storage;
    }
    
    protected abstract void add(String key, Object value);
    
    public Object get(String key) {
        return storage.get(key);
    }
    
    public boolean contains(String key) {
        return storage.containsKey(key);
    }
    
    public void clear() {
        storage.clear();
    }
}