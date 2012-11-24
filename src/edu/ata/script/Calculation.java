package edu.ata.script;

public abstract class Calculation extends Manipulation {

    private final String sign;

    public Calculation(String literal, String sign) {
        super(literal);
        this.sign = sign;
    }

    Object retrieveValue() {
        String data = getLiteral();
        double calc = ((Number) Data.getData(data.substring(0, data.indexOf(sign))).getValue()).doubleValue();
        data = data.substring(data.indexOf(sign) + 1);
        while (StringUtils.contains(data, sign)) {
            calc = doCalc(calc, ((Number) Data.getData(data.substring(0, data.indexOf(sign))).getValue()).doubleValue());
            data = data.substring(data.indexOf(sign) + 1);
        }
        calc = doCalc(calc, ((Number) Data.getData(data).getValue()).doubleValue());
        return Double.valueOf(calc);
    }

    public abstract double doCalc(double val1, double val2);
}
