package edu.gordian.scopes;

import edu.gordian.Strings;

final class If extends Scope {

    private final String condition;

    public If(String condition, Scope scope) {
        super(scope);
        this.condition = condition;
    }

    public void run(String script) throws Exception {
        String i = script, e = "";
        if(Strings.contains(script, "else;")) {
            i = script.substring(0, script.indexOf("else;"));
            e = script.substring(script.indexOf("else;") + 5);
        }
        if (((Boolean) toValue(condition).getValue()).booleanValue()) {
            super.run(i);
        } else {
            super.run(e);
        }
    }
}
