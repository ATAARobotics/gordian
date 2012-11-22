package edu.ata.script;

public class Concatenation extends Manipulation {

    public static boolean isConcatenation(String data) {
        while (StringUtils.contains(data, "+")) {
            try {
                Double.parseDouble(data.substring(0, data.indexOf("+")));
            } catch (NumberFormatException ex) {
                return true;
            }
            data = data.substring(data.indexOf("+") + 1);
        }
        return false;
    }

    public Concatenation(String literal) {
        super(literal);
    }

    public Object getValue() {
        String data = getLiteral();
        String value = "";
        while (StringUtils.contains(data, "+")) {
            value += Data.getData(data.substring(0, data.indexOf("+"))).getValue().toString();
            data = data.substring(data.indexOf("+") + 1);
        }
        value += Data.getData(data).getValue().toString();
        return value;
    }
}
