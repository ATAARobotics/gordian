package edu.ata.script.data.doubles;

import edu.ata.script.Data;
import edu.ata.script.data.Double;
import edu.ata.script.data.Integer;

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
        if (data.startsWith("-")) {
            data = "0 " + data;
        }
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
            } else if(Division.isType(data)) {
                return Division.get(data);
            }else {
                return Multiplication.get(data);
            }
        } else {
            throw new RuntimeException("Tried to do calculation on non-calculation"
                    + " - " + data);
        }
    }
    private final Double num1, num2;

    protected Calculation(String literalString, char sign) {
        super(literalString);
        // This may look confusing, but it ensures that in -x - n = y, y is actually equal to 0 - x - n
        // Otherwise, subtraction uses the first '-' as its sign and concludes as non-subtraction
        Data d1 = Data.get(literalString.substring(0, literalString.indexOf(sign))),
                d2 = Data.get(literalString.substring(literalString.indexOf(sign) + 1));
        if (d1 instanceof Integer) {
            d1 = ((Integer) d1).convert();
        }
        if (d2 instanceof Integer) {
            d2 = ((Integer) d2).convert();
        }
        this.num1 = (Double) d1;
        this.num2 = (Double) d2;
        System.out.println("Calc - "+this.getClass().getSimpleName()+" - "+num1.get()+" of "+num2.get());
    }

    public final Object getValue() {
        return doCalc(num1, num2);
    }

    protected abstract java.lang.Double doCalc(edu.ata.script.data.Double num1,
            edu.ata.script.data.Double num2);
}