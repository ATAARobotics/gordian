package edu.ata.script;

public abstract class ReturningMethod extends Data {

    public static boolean isReturningMethod(String data) {
        try {
            return ReturningMethods.contains(data.substring(0, data.indexOf("(")).trim());
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }
    private String name;
    private String[] args;

    public ReturningMethod(String literal) {
        super(literal);
        try {
            this.name = literal.substring(0, literal.indexOf("(")).trim();
            this.args = StringUtils.split(literal.substring(literal.indexOf("("),
                    literal.lastIndexOf(")")), ',');
        } catch (Exception ex) {
            this.name = literal;
            this.args = new String[0];
        }
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return getValue(args);
    }

    public abstract Data getValue(String[] args);
}
