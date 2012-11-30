package edu.ata.script.data.booleans;

import edu.ata.script.Data;
import edu.ata.script.StringUtils;
import edu.ata.script.data.BooleanData;

/**
 * @author Joel Gallant
 */
public class Condition extends BooleanData {

    /**
     *
     * @param data
     * @return
     */
    public static boolean isType(String data) {
        return StringUtils.contains(data, ">")
                || StringUtils.contains(data, "<")
                || StringUtils.contains(data, "!=")
                || StringUtils.contains(data, "==");
    }

    /**
     *
     * @param data
     * @return
     */
    public static Data get(String data) {
        if (isType(data)) {
            return new Condition(data);
        } else {
            throw new RuntimeException("Could not create condition - " + data);
        }
    }

    /**
     *
     * @param literalString
     */
    public Condition(String literalString) {
        super(literalString);
    }

    /**
     *
     * @return
     */
    public Object getValue() {
        // Checks every time (in case of updating values?)
        return Boolean.valueOf(isTrue());
    }

    private boolean isTrue() {
        try {
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
                try {
                    double double1 = Double.parseDouble(data1.getValue().toString());
                    double double2 = Double.parseDouble(data2.getValue().toString());
                    return equals ? double1 == double2 : double1 != double2;
                } catch (NumberFormatException ex) {
                }
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
                    return double1 >= double2;
                } else {
                    return double1 > double2;
                }
            } else if (test == '<') {
                // Boilerplate
                double double1 = Double.parseDouble(data1.getValue().toString());
                double double2 = Double.parseDouble(data2.getValue().toString());
                if (equals) {
                    return double1 <= double2;
                } else {
                    return double1 < double2;
                }
            } else {
                return false;
            }
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}