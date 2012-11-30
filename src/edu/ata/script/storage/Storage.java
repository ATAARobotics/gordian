package edu.ata.script.storage;

import java.util.Enumeration;
import java.util.Hashtable;

/**
 * @author Joel Gallant
 */
public abstract class Storage {

    private final Hashtable storage = new Hashtable();

    /**
     *
     * @return
     */
    protected Hashtable getStorage() {
        return storage;
    }

    /**
     *
     * @param key
     * @param value
     */
    protected abstract void add(String key, Object value);

    /**
     *
     * @param key
     * @return
     */
    public Object get(String key) {
        return storage.get(key);
    }

    /**
     *
     * @return
     */
    public Enumeration keys() {
        return storage.keys();
    }

    /**
     *
     * @param key
     * @return
     */
    public boolean contains(String key) {
        return storage.containsKey(key);
    }

    /**
     *
     * @return
     */
    public int size() {
        return storage.size();
    }

    /**
     *
     */
    public void clear() {
        storage.clear();
    }
}