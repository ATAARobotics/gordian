package edu.ata.script;

public abstract class Calculation extends Manipulation {

    private final String sign;
    
    public Calculation(String literal, String sign) {
        super(literal);
        this.sign = sign;
    }

    public Object getValue() {
        String data = getLiteral();
        double value = ((Number) Data.getData(data.substring(0, data.indexOf(sign))).getValue()).doubleValue();
        data = data.substring(data.indexOf(sign) + 1);
        while (StringUtils.contains(data, sign)) {
            value = doCalc(value, ((Number) Data.getData(data.substring(0, data.indexOf(sign))).getValue()).doubleValue());
            data = data.substring(data.indexOf(sign) + 1);
        }
        value = doCalc(value, ((Number) Data.getData(data).getValue()).doubleValue());
        return Double.valueOf(value);
    }

    public abstract double doCalc(double val1, double val2);
}
