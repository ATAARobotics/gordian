package edu.ata.script.storage;

import java.util.Enumeration;
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

    public Enumeration keys() {
        return storage.keys();
    }

    public boolean contains(String key) {
        return storage.containsKey(key);
    }

    public int size() {
        return storage.size();
    }

    public void clear() {
        storage.clear();
    }
}