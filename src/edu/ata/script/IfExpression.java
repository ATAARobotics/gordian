package edu.ata.script;

public class IfExpression extends Expression {

    public static boolean isIf(String expression) {
        return expression.indexOf("if(") == 0 && expression.indexOf(")") > 0;
    }
    
    public IfExpression(String f) {
        super(f);
    }

    public void run() {
        Object value = Data.getData(expression.substring(
                expression.indexOf("(") + 1, expression.lastIndexOf(")"))).getValue();
        if(value instanceof Boolean && ((Boolean)value).booleanValue()) {
            super.run();
        }
    }
}
