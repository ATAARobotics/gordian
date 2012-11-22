package edu.ata.script;

public abstract class Data {

    public static Data getData(final String literal) {
        Data d;
        // Primitives
        try {
            final Integer i = new Integer(literal);
            d = new Data(literal) {
                public Object getValue() {
                    return i;
                }
            };
        } catch (NumberFormatException ex1) {
            try {
                final Double d1 = new Double(literal);
                d = new Data(literal) {
                    public Object getValue() {
                        return d1;
                    }
                };
            } catch (NumberFormatException ex2) {
                if (literal.equalsIgnoreCase("true") || literal.equalsIgnoreCase("false")) {
                    d = new Data(literal) {
                        public Object getValue() {
                            return Boolean.valueOf(literal);
                        }
                    };
                } else if (Conditional.isCondional(literal)) {
                    d = new Conditional(literal);
                } else if (Manipulation.isManipulation(literal)) {
                    d = Manipulation.getValue(literal);
                } else if (VariableQueue.containsKey(literal)) {
                    d = VariableQueue.get(literal);
                } else if (ReturningMethod.isReturningMethod(literal)) {
                    d = ReturningMethods.get(literal);
                } else {
                    d = new Data(literal) {
                        public Object getValue() {
                            // Removes all quotation marks from strings
                            return StringUtils.replace(literal.trim(), '\"', "");
                        }
                    };
                }
            }
        }
        return d;
    }
    private final String literal;

    public Data(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }

    public String toString() {
        return getLiteral();
    }

    public abstract Object getValue();
}
