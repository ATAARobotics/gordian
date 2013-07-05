package edu.gordian.scopes;

import edu.gordian.elements.methods.MethodBase;
import edu.gordian.values.ReturningMethodBase;
import edu.gordian.values.Value;

final class DefinedMethod extends Scope implements MethodBase {

    private final String[] args;
    private final String script;
    private Object returnedValue;

    public DefinedMethod(String[] args, String script, Scope scope) {
        super(scope);
        this.args = args;
        this.script = script;
    }

    public void run(Value[] arguments) {
        if(arguments.length < args.length) {
            throw new IllegalArgumentException("Not enough arguments");
        }
        for (int x = 0; x < args.length; x++) {
            // CANNOT REDECLARE VARS IN A DEF
            if (getVariable(args[x]) == null) {
                setPrivateVariable(args[x], arguments[x]);
            }
        }
        addMethod("return", new MethodBase() {
            public void run(Value[] arguments) {
                if (arguments.length < 1) {
                    returnedValue = null;
                } else {
                    returnedValue = arguments[0];
                }
            }
        });

        try {
            run(script);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public ReturningMethodBase returningMethod() {
        return new ReturningMethodBase() {
            public Object run(Value[] arguments) {
                DefinedMethod.this.run(arguments);
                return returnedValue;
            }
        };
    }
}
