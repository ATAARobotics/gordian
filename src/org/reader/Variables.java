package org.reader;

import java.util.Vector;

/**
 * Static cache of variables. Stores declared variables.
 *
 * @author joel
 */
public class Variables {

    private static final Vector names = new Vector();
    private static final Vector variables = new Vector();

    /**
     * Adds a variable to the cache.
     *
     * @param name variable name
     * @param value value of the variable
     */
    public static void add(String name, Value value) {
        names.add(name);
        variables.add(value);
    }

    /**
     * Sets a variable in the cache.
     *
     * @param name variable name
     * @param value value of the variable
     */
    public static void set(String name, Value value) {
        variables.setElementAt(value, names.indexOf(name));
    }

    /**
     * Returns the value of a variable in the cache.
     *
     * @param name variable name
     * @return value of the variable
     */
    public static Value get(String name) {
        return (Value) variables.get(names.indexOf(name));
    }

    /**
     * Returns whether the variable is being stored in the cache.
     *
     * @param name variable name
     * @return if the variable exists in the cache
     */
    public static boolean contains(String name) {
        return names.contains(name);
    }
}
