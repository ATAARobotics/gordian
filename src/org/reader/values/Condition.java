package org.reader.values;

import org.reader.Value;

/**
 * Condition object used for comparisons. Results in boolean value.
 *
 * @author joel
 */
public final class Condition {

    /**
     * Returns a condition based on it's pure value. Does not change values once
     * this method is called. Must recall this method to update value.
     *
     * @param value value of the condition
     * @return the condition object representing the condition
     */
    public static Condition getConditionFrom(Value value) {
        String statement = value.toString();
        if (statement.contains("!=")) {
            String v1 = statement.substring(0, statement.indexOf("!=")).trim();
            String v2 = statement.substring(statement.indexOf("!=") + 2).trim();
            return new Condition(new Value(v1), new Value(v2), false);
        } else if (statement.contains("==")) {
            String v1 = statement.substring(0, statement.indexOf("==")).trim();
            String v2 = statement.substring(statement.indexOf("==") + 2).trim();
            return new Condition(new Value(v1), new Value(v2), true);
        } else if (statement.contains(">=")) {
            String v1 = statement.substring(0, statement.indexOf(">=")).trim();
            String v2 = statement.substring(statement.indexOf(">=") + 2).trim();
            return new Condition(new Value(v1), new Value(v2), '>', true);
        } else if (statement.contains("<=")) {
            String v1 = statement.substring(0, statement.indexOf("<=")).trim();
            String v2 = statement.substring(statement.indexOf("<=") + 2).trim();
            return new Condition(new Value(v1), new Value(v2), '<', true);
        } else if (statement.contains("<") && !statement.contains("=")) {
            String v1 = statement.substring(0, statement.indexOf("<")).trim();
            String v2 = statement.substring(statement.indexOf("<") + 1).trim();
            return new Condition(new Value(v1), new Value(v2), '<', false);
        } else if (statement.contains(">") && !statement.contains("=")) {
            String v1 = statement.substring(0, statement.indexOf(">")).trim();
            String v2 = statement.substring(statement.indexOf(">") + 1).trim();
            return new Condition(new Value(v1), new Value(v2), '>', false);
        } else {
            return new Condition(value);
        }
    }
    private final Value v1,
            v2;
    private final boolean equals;
    private final char test;

    private Condition(Value value) {
        v1 = v2 = value;
        equals = true;
        test = 't';
    }

    private Condition(Value v1, Value v2, boolean equals) {
        this(v1, v2, '=', equals);
    }

    private Condition(Value v1, Value v2, char test, boolean equals) {
        this.v1 = v1;
        this.v2 = v2;
        this.equals = equals;
        this.test = test;
    }

    @Override
    public String toString() {
        return v1.toString() + test + (equals ? "=" : "") + v2.toString();
    }

    /**
     * Returns if the condition is true.
     *
     * @return if true
     */
    public boolean isTrue() {
        if (v1.getValue().toString().equalsIgnoreCase("true")) {
            return true;
        }
        if (test == '=') {
            if ((equals && v1.equals(v2)) || (!equals && !v1.equals(v2))) {
                return true;
            } else {
                return false;
            }
        } else if (test == '>') {
            // Needs to be a number value. Throws error if not.
            double value1 = Double.parseDouble(v1.getValue().toString());
            double value2 = Double.parseDouble(v2.getValue().toString());
            if (equals) {
                if (v1.equals(v2) || value1 > value2) {
                    // >= (== or >)
                    return true;
                } else {
                    // Does not satify >=
                    return false;
                }
            } else {
                if (value1 > value2) {
                    // >
                    return true;
                } else {
                    // Does not satisfy >
                    return false;
                }
            }
        } else if (test == '<') {
            // Boilerplate
            double value1 = Double.parseDouble(v1.getValue().toString());
            double value2 = Double.parseDouble(v2.getValue().toString());
            if (equals) {
                if (v1.equals(v2) || value1 < value2) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (value1 < value2) {
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }
}
