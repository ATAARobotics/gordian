package org.reader.values;

import org.reader.Value;

/**
 * Condition object used for comparisons. Results in boolean value.
 *
 * @author joel
 */
public final class Condition {

    public static boolean isCondition(String value) {
        return value.contains("==") || value.contains("!=") || value.contains(">")
                || value.contains("<") || Value.isBoolean(value);
    }

    /**
     * Returns a condition based on it's pure value. Does not change values once
     * this method is called. Must recall this method to update value.
     *
     * @param value value of the condition
     * @return the condition object representing the condition
     */
    public static Condition getConditionFrom(String value) {
        return new Condition(value);
    }
    private final String statement;

    private Condition(String condition) {
        this.statement = condition;
    }

    @Override
    public String toString() {
        return statement;
    }

    /**
     * Returns if the condition is true.
     *
     * @return if true
     */
    public boolean isTrue() {
        Value value1, value2;
        char test;
        boolean equals;
        if (statement.contains("!=")) {
            String v1 = statement.substring(0, statement.indexOf("!=")).trim();
            String v2 = statement.substring(statement.indexOf("!=") + 2).trim();
            value1 = new Value(v1);
            value2 = new Value(v2);
            test = '=';
            equals = false;
        } else if (statement.contains("==")) {
            String v1 = statement.substring(0, statement.indexOf("==")).trim();
            String v2 = statement.substring(statement.indexOf("==") + 2).trim();
            value1 = new Value(v1);
            value2 = new Value(v2);
            test = '=';
            equals = true;
        } else if (statement.contains(">=")) {
            String v1 = statement.substring(0, statement.indexOf(">=")).trim();
            String v2 = statement.substring(statement.indexOf(">=") + 2).trim();
            value1 = new Value(v1);
            value2 = new Value(v2);
            test = '>';
            equals = true;
        } else if (statement.contains("<=")) {
            String v1 = statement.substring(0, statement.indexOf("<=")).trim();
            String v2 = statement.substring(statement.indexOf("<=") + 2).trim();
            value1 = new Value(v1);
            value2 = new Value(v2);
            test = '<';
            equals = true;
        } else if (statement.contains("<") && !statement.contains("=")) {
            String v1 = statement.substring(0, statement.indexOf("<")).trim();
            String v2 = statement.substring(statement.indexOf("<") + 1).trim();
            value1 = new Value(v1);
            value2 = new Value(v2);
            test = '<';
            equals = false;
        } else if (statement.contains(">") && !statement.contains("=")) {
            String v1 = statement.substring(0, statement.indexOf(">")).trim();
            String v2 = statement.substring(statement.indexOf(">") + 1).trim();
            value1 = new Value(v1);
            value2 = new Value(v2);
            test = '>';
            equals = false;
        } else if (statement.equalsIgnoreCase("true")
                || new Value(statement).getValue().toString().equalsIgnoreCase("true")) {
            return true;
        } else {
            return false;
        }
        if (test == '=') {
            // == and equals
            if ((equals && value1.equals(value2))
                    // != and not equals
                    || (!equals && !value1.equals(value2))) {
                return true;
            } else {
                return false;
            }
        } else if (test == '>') {
            // Needs to be a number value. Throws error if not.
            double double1 = Double.parseDouble(value1.getValue().toString());
            double double2 = Double.parseDouble(value2.getValue().toString());
            if (equals) {
                if (value1.equals(value2) || double1 > double2) {
                    // >= (== or >)
                    return true;
                } else {
                    // Does not satify >=
                    return false;
                }
            } else {
                if (double1 > double2) {
                    // >
                    return true;
                } else {
                    // Does not satisfy >
                    return false;
                }
            }
        } else if (test == '<') {
            // Boilerplate
            double double1 = Double.parseDouble(value1.getValue().toString());
            double double2 = Double.parseDouble(value2.getValue().toString());
            if (equals) {
                if (value1.equals(value2) || double1 < double2) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (double1 < double2) {
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
