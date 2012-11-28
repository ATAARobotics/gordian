package edu.ata.script.data.doubles;

import edu.ata.script.Data;
import edu.ata.script.data.DoubleData;
import edu.ata.script.data.NumberData;

/**
 * @author Joel Gallant
 */
public abstract class Calculation extends DoubleData {

    public static boolean isType(String data) {
        return Addition.isType(data)
                || Subtraction.isType(data)
                || Multiplication.isType(data)
                || Division.isType(data);
    }

    public static Data get(String data) {
        // Addition and subtraction are first to make sure it goes in order
        // - Division - Multiplication - Subtraction - Addition -
        // This is to give it as close of a resemblance to PEDMAS (DMAS)
        if (Addition.isType(data) || Subtraction.isType(data)) {
            if (data.indexOf('-') < data.indexOf('+') && Subtraction.isType(data)) {
                return Subtraction.get(data);
            } else if (Addition.isType(data)) {
                return Addition.get(data);
            } else {
                return Subtraction.get(data);
            }
        } else if (Multiplication.isType(data) || Division.isType(data)) {
            if (data.indexOf('*') < data.indexOf('/') && Multiplication.isType(data)) {
                return Multiplication.get(data);
            } else if (Division.isType(data)) {
                return Division.get(data);
            } else {
                return Multiplication.get(data);
            }
        } else {
            throw new RuntimeException("Tried to do calculation on non-calculation"
                    + " - " + data);
        }
    }
    private final double num1, num2;

    protected Calculation(String literalString, char sign) {
        super(literalString);
        if (literalString.indexOf(sign) == 0) {
            this.num1 = NumberData.doubleValue(
                    literalString.substring(0, literalString.substring(1).indexOf(sign)));
            this.num2 = NumberData.doubleValue(
                    literalString.substring(literalString.substring(1).indexOf(sign) + 1));
        } else {
            this.num1 = NumberData.doubleValue(
                    literalString.substring(0, literalString.indexOf(sign)));
            this.num2 = NumberData.doubleValue(
                    literalString.substring(literalString.indexOf(sign) + 1));
        }
    }

    public final Object getValue() {
        return doCalc(num1, num2);
    }

    protected abstract Double doCalc(double num1, double num2);
}