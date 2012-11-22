package edu.ata.script;

public class Subtraction extends Manipulation {

    public static boolean isSubtraction(String data) {
        if (!StringUtils.contains(data, "-")) {
            return false;
        }
        while (StringUtils.contains(data, "-")) {
            if (!(Data.getData(data.substring(0, data.indexOf("-"))).getValue() instanceof Number)) {
                return false;
            }
            data = data.substring(data.indexOf("-") + 1);
        }
        return true;
    }

    public Subtraction(String literal) {
        super(literal);
    }

    public Object getValue() {
        String data = getLiteral();
        double value = ((Number) Data.getData(data.substring(0, data.indexOf("-"))).getValue()).doubleValue();
        data = data.substring(data.indexOf("-") + 1);
        while (StringUtils.contains(data, "-")) {
            value -= ((Number) Data.getData(data.substring(0, data.indexOf("-"))).getValue()).doubleValue();
            data = data.substring(data.indexOf("-") + 1);
        }
        value -= ((Number) Data.getData(data).getValue()).doubleValue();
        return Double.valueOf(value);
    }
}
