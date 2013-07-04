package edu.gordian.scopes;

class While extends Scope {

    private final String condition;

    public While(String condition, Scope scope) {
        super(scope);
        this.condition = condition;
    }

    public void run(String script) throws Exception {
        while (((Boolean) toValue(condition).getValue()).booleanValue()) {
            super.run(script);
        }
    }
}
