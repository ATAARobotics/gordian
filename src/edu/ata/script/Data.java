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

                public boolean isNumber() {
                    return true;
                }
            };
        } catch (NumberFormatException ex1) {
            try {
                final Double d1 = new Double(literal);
                d = new Data(literal) {
                    public Object getValue() {
                        return d1;
                    }

                    public boolean isNumber() {
                        return true;
                    }
                };
            } catch (NumberFormatException ex2) {
                if (literal.equalsIgnoreCase("true") || literal.equalsIgnoreCase("false")) {
                    d = new Data(literal) {
                        public Object getValue() {
                            return Boolean.valueOf(literal);
                        }

                        public boolean isNumber() {
                            return false;
                        }
                    };
                } else if (Conditional.isCondional(literal)) {
                    d = new Conditional(literal);
                } else {
                    d = new Data(literal) {
                        public Object getValue() {
                            return literal;
                        }

                        public boolean isNumber() {
                            return false;
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

    public abstract boolean isNumber();
}
