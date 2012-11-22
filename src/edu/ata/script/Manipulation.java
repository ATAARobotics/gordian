package edu.ata.script;

public abstract class Manipulation extends Data {

    public static boolean isManipulation(String data) {
        return Concatenation.isConcatenation(data)
                || Addition.isAddition(data)
                || Subtraction.isSubtraction(data)
                || Multiplication.isMultiplication(data)
                || Division.isDivision(data);
    }

    public static Manipulation getValue(String data) {
        if (Addition.isAddition(data)) {
            return new Addition(data);
        } else if (Subtraction.isSubtraction(data)) {
            return new Subtraction(data);
        } else if (Multiplication.isMultiplication(data)) {
            return new Multiplication(data);
        } else if (Division.isDivision(data)) {
            return new Division(data);
        } else if (Concatenation.isConcatenation(data)) {
            return new Concatenation(data);
        }
        throw new NullPointerException("Tried to get Manipulation from non-manipulator - " + data);
    }

    public Manipulation(String literal) {
        super(literal);
    }
}
