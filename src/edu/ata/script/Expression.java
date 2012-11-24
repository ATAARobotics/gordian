package edu.ata.script;

import java.util.Vector;

public class Expression extends Instruction {

    public static boolean isExpression(String instruction) {
        if (!StringUtils.contains(instruction, "{") || !StringUtils.contains(instruction, "}")) {
            return false;
        }
        if (IfExpression.isIf(instruction)) {
            return true;
        } else if (instruction.indexOf("{") == 0) {
            return true;
        } else {
            return false;
        }
    }

    public static Expression getExpression(String instruction) {
        if (IfExpression.isIf(instruction)) {
            return new IfExpression(instruction);
        } else {
            // Does everthing in the brackets if expression is malformed
            return new Expression(instruction);
        }
    }
    private final Vector instructions = new Vector();
    protected final String expression;

    public Expression(String f) {
        super(f);
        this.expression = f;
        String full = StringUtils.replace(f.substring(f.indexOf("{;") + 2, 
                f.indexOf("};")), '\n', ";");
        InstructionIterator iterator = new InstructionIterator(full);
        while (iterator.hasNext()) {
            try {
                instructions.add(iterator.getNext());
            } catch (NullPointerException ex) {
                System.err.println("Error! - Expression contains non-instruction\n    "
                        + ex.getMessage());
                break;
            }
        }
    }

    public void run() {
        for (int x = 0; x < instructions.size(); x++) {
            ((Instruction) instructions.elementAt(x)).run();
        }
    }
}
