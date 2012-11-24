package edu.ata.script;

public class Conditional extends Data {

    public static boolean isCondional(String data) {
        return StringUtils.contains(data, "==") || StringUtils.contains(data, "!=")
                || StringUtils.contains(data, ">") || StringUtils.contains(data, "<");
    }

    public Conditional(String literal) {
        super(literal);
    }

    Object retrieveValue() {
        return Boolean.valueOf(isTrue());
    }

    private boolean isTrue() {
        Data data1, data2;
        char test;
        boolean equals;
        if (StringUtils.contains(getLiteral(), "!=")) {
            String v1 = getLiteral().substring(0, getLiteral().indexOf("!=")).trim();
            String v2 = getLiteral().substring(getLiteral().indexOf("!=") + 2).trim();
            data1 = Data.getData(v1);
            data2 = Data.getData(v2);
            test = '=';
            equals = false;
        } else if (StringUtils.contains(getLiteral(), "==")) {
            String v1 = getLiteral().substring(0, getLiteral().indexOf("==")).trim();
            String v2 = getLiteral().substring(getLiteral().indexOf("==") + 2).trim();
            data1 = Data.getData(v1);
            data2 = Data.getData(v2);
            test = '=';
            equals = true;
        } else if (StringUtils.contains(getLiteral(), ">=")) {
            String v1 = getLiteral().substring(0, getLiteral().indexOf(">=")).trim();
            String v2 = getLiteral().substring(getLiteral().indexOf(">=") + 2).trim();
            data1 = Data.getData(v1);
            data2 = Data.getData(v2);
            test = '>';
            equals = true;
        } else if (StringUtils.contains(getLiteral(), "<=")) {
            String v1 = getLiteral().substring(0, getLiteral().indexOf("<=")).trim();
            String v2 = getLiteral().substring(getLiteral().indexOf("<=") + 2).trim();
            data1 = Data.getData(v1);
            data2 = Data.getData(v2);
            test = '<';
            equals = true;
        } else if (StringUtils.contains(getLiteral(), "<") && !StringUtils.contains(getLiteral(), "=")) {
            String v1 = getLiteral().substring(0, getLiteral().indexOf("<")).trim();
            String v2 = getLiteral().substring(getLiteral().indexOf("<") + 1).trim();
            data1 = Data.getData(v1);
            data2 = Data.getData(v2);
            test = '<';
            equals = false;
        } else if (StringUtils.contains(getLiteral(), ">") && !StringUtils.contains(getLiteral(), "=")) {
            String v1 = getLiteral().substring(0, getLiteral().indexOf(">")).trim();
            String v2 = getLiteral().substring(getLiteral().indexOf(">") + 1).trim();
            data1 = Data.getData(v1);
            data2 = Data.getData(v2);
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
            double double1 = Double.parseDouble(data1.getLiteral());
            double double2 = Double.parseDouble(data2.getLiteral());
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
            double double1 = Double.parseDouble(data1.getLiteral());
            double double2 = Double.parseDouble(data2.getLiteral());
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
