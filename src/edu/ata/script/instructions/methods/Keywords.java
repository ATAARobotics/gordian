package edu.ata.script.instructions.methods;

import java.util.HashMap;
import edu.ata.script.base.Value;
import edu.ata.script.instructions.Method;

/**
 * Cache that stores all user-defined method. Native methods are stored in
 * {@link NativeMethods}.
 *
 * @author joel
 */
public class Keywords {

    private static final HashMap methods = new HashMap();

    /**
     * Adds a user-defined method.
     *
     * @param m method to add
     */
    public static void add(Method m) {
        Keywords.methods.put(m.getName(), m);
    }

    /**
     * Returns whether a method with the name exists.
     *
     * @param name name of the method
     * @return if it exists in the cache
     */
    public static boolean contains(String name) {
        return methods.containsKey(name);
    }

    /**
     * Returns a method based on it's name and arguments. Useful for running
     * defined methods.
     *
     * @param name name of the method
     * @param args arguments to set the method
     * @return method based on name
     * @throws NoSuchFieldException thrown when method does not exist. Use
     * {@link Keywords#contains(java.lang.String)} to check.
     */
    public static Method get(String name, Value[] args) throws NoSuchFieldException {
        if (!contains(name)) {
            throw new NoSuchFieldException("The field " + name + " does not exist in Keywords.");
        }
        return Method.newMethod((Method) methods.get(name), args);
    }
}
