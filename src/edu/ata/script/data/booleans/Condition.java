package edu.ata.script.data.booleans;

import edu.ata.script.Data;
import edu.ata.script.StringUtils;

/**
 * @author Joel Gallant
 */
public class Condition extends edu.ata.script.data.Boolean {

    public static boolean isType(java.lang.String data) {
        return StringUtils.contains(data, ">")
                || StringUtils.contains(data, "<")
                || StringUtils.contains(data, "!=")
                || StringUtils.contains(data, "==");
    }

    public static Data get(java.lang.String data) {
        if (isType(data)) {
            return new Condition(data);
        } else {
            throw new RuntimeException("Could not create condition - " + data);
        }
    }

    public Condition(String literalString) {
        super(literalString);
    }

    public Object getValue() {
        return Boolean.valueOf(isTrue());
    }

    private boolean isTrue() {
        String literal = getLiteralString();
        Data data1, data2;
        char test;
        boolean equals;
        if (StringUtils.contains(literal, "!=")) {
            String v1 = literal.substring(0, literal.indexOf("!="));
            String v2 = literal.substring(literal.indexOf("!=") + 2);
            data1 = Data.get(v1);
            data2 = Data.get(v2);
            test = '=';
            equals = false;
        } else if (StringUtils.contains(literal, "==")) {
            String v1 = literal.substring(0, literal.indexOf("=="));
            String v2 = literal.substring(literal.indexOf("==") + 2);
            data1 = Data.get(v1);
            data2 = Data.get(v2);
            test = '=';
            equals = true;
        } else if (StringUtils.contains(literal, ">=")) {
            String v1 = literal.substring(0, literal.indexOf(">="));
            String v2 = literal.substring(literal.indexOf(">=") + 2);
            data1 = Data.get(v1);
            data2 = Data.get(v2);
            test = '>';
            equals = true;
        } else if (StringUtils.contains(literal, "<=")) {
            String v1 = literal.substring(0, literal.indexOf("<="));
            String v2 = literal.substring(literal.indexOf("<=") + 2);
            data1 = Data.get(v1);
            data2 = Data.get(v2);
            test = '<';
            equals = true;
        } else if (StringUtils.contains(literal, "<") && !StringUtils.contains(literal, "=")) {
            String v1 = literal.substring(0, literal.indexOf("<"));
            String v2 = literal.substring(literal.indexOf("<") + 1);
            data1 = Data.get(v1);
            data2 = Data.get(v2);
            test = '<';
            equals = false;
        } else if (StringUtils.contains(literal, ">") && !StringUtils.contains(literal, "=")) {
            String v1 = literal.substring(0, literal.indexOf(">"));
            String v2 = literal.substring(literal.indexOf(">") + 1);
            data1 = Data.get(v1);
            data2 = Data.get(v2);
            test = '>';
            equals = false;
        } else {
            return false;
        }
        if (test == '=') {
            // == and equals
            if ((equals && data1.getValue().equals(data2.getValue()))
                    // != and not equals
                    || (!equals && !data1.getValue().equals(data2.getValue()))) {
                return true;
            } else {
                return false;
            }
        } else if (test == '>') {
            // Needs to be a number data. Throws error if not.
            double double1 = Double.parseDouble(data1.getValue().toString());
            double double2 = Double.parseDouble(data2.getValue().toString());
            if (equals) {
                if (data1.getValue().equals(data2.getValue()) || double1 > double2) {
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
            double double1 = Double.parseDouble(data1.getValue().toString());
            double double2 = Double.parseDouble(data2.getValue().toString());
            if (equals) {
                if (data1.getValue().equals(data2.getValue()) || double1 < double2) {
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