package edu.ata.script;

public class Division extends Calculation {

    public static boolean isDivision(String data) {
        if (!StringUtils.contains(data, "/")) {
            return false;
        }
        while (StringUtils.contains(data, "/")) {
            if (!(Data.getData(data.substring(0, data.indexOf("/"))).getValue() instanceof Number)) {
                return false;
            }
            data = data.substring(data.indexOf("/") + 1);
        }
        return true;
    }
    public Division(String literal) {
        super(literal, "/");
    }

    public double doCalc(double val1, double val2) {
        return val1 / val2;
    }
}
