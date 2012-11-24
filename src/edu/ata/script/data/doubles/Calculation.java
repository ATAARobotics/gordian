package edu.ata.script.data.doubles;

import edu.ata.script.Data;

/**
 * @author Joel Gallant
 */
public abstract class Calculation extends edu.ata.script.data.Double {

    public static boolean isType(java.lang.String data) {
        return Addition.isType(data)
                || Subtraction.isType(data)
                || Multiplication.isType(data)
                || Division.isType(data);
    }

    public static Data get(java.lang.String data) {
        // Addition and subtraction are first to make sure it goes in order
        // - Division - Multiplication - Subtraction - Addition -
        // This is to give it as close of a resemblance to PEDMAS (DMAS)
        if (Addition.isType(data)) {
            return Addition.get(data);
        } else if (Subtraction.isType(data)) {
            return Subtraction.get(data);
        } else if (Multiplication.isType(data)) {
            return Multiplication.get(data);
        } else if (Division.isType(data)) {
            return Division.get(data);
        } else {
            throw new RuntimeException("Tried to do calculation on non-calculation"
                    + " - " + data);
        }
    }
    private final char sign;

    protected Calculation(String literalString, char sign) {
        super(literalString);
        this.sign = sign;
    }

    protected Object getValue() {
        return doCalc((edu.ata.script.data.Double) edu.ata.script.data.Double.get(
                getLiteralString().substring(0, getLiteralString().indexOf(sign))),
                (edu.ata.script.data.Double) edu.ata.script.data.Double.get(
                getLiteralString().substring(getLiteralString().indexOf(sign) + 1)));
    }

    protected abstract java.lang.Double doCalc(edu.ata.script.data.Double num1,
            edu.ata.script.data.Double num2);
}