package edu.ata.script;

public abstract class Instruction {

    public static boolean isInstruction(String instruction) {
        if (instruction.equals("};")) {
            return true;
        } else if (instruction.length() <= 0 || !StringUtils.contains(instruction, ";")) {
            return false;
        } else if (Assignment.isAssignment(instruction)) {
            return true;
        }
        return false;
    }

    public static Instruction getInstruction(String instruction) throws NullPointerException {
        if (Expression.isExpression(instruction)) {
            return new Expression(instruction);
        } else {
            instruction = instruction.substring(0, instruction.indexOf(";"));
            if (instruction.equals("}")) {
                return new BlankInstruction(instruction);
            } else if (Assignment.isAssignment(instruction)) {
                return new Assignment(instruction);
            }
        }
        throw new NullPointerException("Invalid instruction - " + instruction);
    }

    public static Instruction getNextInstruction(String full) throws NullPointerException {
        if (!isInstruction(full)) {
            throw new NullPointerException("Tried to create instruction where "
                    + "none exists - \"" + full.substring(0,
                    full.indexOf(";") > -1 ? full.indexOf(";") : 0) + "\"");
        }
        return getInstruction(full);
    }
    private final String instruction;

    public Instruction(String instruction) {
        this.instruction = instruction;
    }

    public String getLiteral() {
        return instruction;
    }

    public String toString() {
        return getLiteral();
    }

    public abstract void run();
}
