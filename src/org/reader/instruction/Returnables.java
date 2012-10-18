package org.reader.instruction;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Vector;
import org.reader.Value;

/**
 * Static class that stores all returnable methods including native methods.
 *
 * @author joel
 */
public class Returnables {

    private static final Vector names = new Vector();
    private static final Vector returnables = new Vector();

    static {
        add(new MathMethod("SUM") {
            @Override
            public BigDecimal doCalc(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
                return bigDecimal1.add(bigDecimal2, MathContext.DECIMAL128);
            }
        });
        add(new MathMethod("SUBTRACT") {
            @Override
            public BigDecimal doCalc(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
                return bigDecimal1.subtract(bigDecimal2, MathContext.DECIMAL128);
            }
        });
        add(new MathMethod("MULTIPLY") {
            @Override
            public BigDecimal doCalc(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
                return bigDecimal1.multiply(bigDecimal2, MathContext.DECIMAL128);
            }
        });
        add(new MathMethod("DIVIDE") {
            @Override
            public BigDecimal doCalc(BigDecimal bigDecimal1, BigDecimal bigDecimal2) {
                return bigDecimal1.divide(bigDecimal2, MathContext.DECIMAL128);
            }
        });
    }

    /**
     * Adds a returnable object to the cache.
     *
     * @param returnable returnable to add
     */
    public static void add(Returnable returnable) {
        names.add(returnable.getName());
        returnables.add(returnable);
    }

    /**
     * Removes a returnable object by it's name
     *
     * @param methodName method name
     */
    public static void remove(String methodName) {
        names.remove(methodName);
        returnables.remove(names.indexOf(methodName));
    }

    /**
     * Returns whether the cache contains a method
     *
     * @param methodName name of the method
     * @return whether cache contains method
     */
    public static boolean contains(String methodName) {
        return names.contains(methodName);
    }

    /**
     * Returns the returnable method based on it's name.
     *
     * @param methodName name of the method
     * @return returnable
     */
    public static Returnable get(String methodName) {
        return ((Returnable) returnables.get(names.indexOf(methodName)));
    }

    /**
     * Returns the value of the returnable based solely on it's method name. Is
     * not checked. (Use {@link Returnables#isValid(java.lang.String)} to check.
     *
     * @param method method name
     * @return value returned by the method
     */
    public static Object getFromMethod(String method) {
        String[] args = method.substring(method.indexOf("(") + 1, method.lastIndexOf(")")).split(",");
        Value[] trueArgs = new Value[args.length];
        for (int x = 0; x < args.length; x++) {
            trueArgs[x] = new Value(args[x].trim());
        }
        return get(Method.getMethodName(method)).getValue(trueArgs);
    }

    /**
     * Returns whether the method is stored in the cache.
     *
     * @param value method name
     * @return whether the method stored
     */
    public static boolean isValid(String value) {
        return contains(Method.getMethodName(value));
    }

    /**
     * Returnable method. Returns a value based on its arguments.
     */
    public static interface Returnable {

        /**
         * Returns the name of the method. Used as Name(Args);
         *
         * @return name of the method
         */
        public abstract String getName();

        /**
         * Returns the value based on its arguments.
         *
         * @param args arguments given
         * @return value
         */
        public abstract Object getValue(Value[] args);
    }

    private static abstract class MathMethod implements Returnable {

        public final String name;

        public MathMethod(String name) {
            this.name = name;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Object getValue(Value[] args) {
            BigDecimal value;
            int start = 0;
            try {
                value = new BigDecimal(Double.parseDouble(args[0].toString()));
                start = 1;
            } catch (Exception ex) {
                value = new BigDecimal(0);
            }
            for (int x = start; x < args.length; x++) {
                if (Value.isDouble(args[x].toString())) {
                    value = doCalc(value, new BigDecimal(Double.parseDouble(args[x].toString())));
                }
            }
            return value;
        }

        public abstract BigDecimal doCalc(BigDecimal bigDecimal1, BigDecimal bigDecimal2);
    }
}
