package org.reader.instruction.methods;

import java.util.Vector;
import org.reader.Value;
import org.reader.instruction.Method;

/**
 * Cache that stores all user-defined method. Native methods are stored in
 * {@link NativeMethods}.
 *
 * @author joel
 */
public class Keywords {

    private static final Vector methods = new Vector();

    /**
     * Adds a user-defined method.
     *
     * @param m method to add
     */
    public static void add(Method m) {
        Keywords.methods.add(m);
    }

    /**
     * Returns whether a method with the name exists.
     *
     * @param name name of the method
     * @return if it exists in the cache
     */
    public static boolean contains(String name) {
        for (int x = 0; x < methods.size(); x++) {
            if (((Method) methods.get(x)).name.equals(name)) {
                return true;
            }
        }
        return false;
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
        for (int x = 0; x < methods.size(); x++) {
            if (((Method) methods.get(x)).name.equals(name)) {
                Method value = ((Method) methods.get(x));
                value.arguments = args;
                return value;
            }
        }
        throw new NoSuchFieldException("The field " + name + " does not exist in Keywords.");
    }
}
