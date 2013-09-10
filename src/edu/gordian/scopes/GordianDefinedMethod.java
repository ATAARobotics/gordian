package edu.gordian.scopes;

import edu.first.util.Strings;
import language.instruction.Method;
import edu.gordian.internal.ValueReturned;
import language.scope.Scope;
import language.value.Value;

public class GordianDefinedMethod extends GordianScope {

    public static void run(Scope scope, String s) {
        new GordianDefinedMethod(scope).define(s.substring(3, s.indexOf("(")),
                Strings.split(s.substring(s.indexOf("(") + 1, s.substring(0, s.indexOf(";")).lastIndexOf(')')), ","),
                s.substring(s.indexOf(";") + 1));
    }

    public GordianDefinedMethod(Scope scope) {
        super(scope);
    }

    public void define(final String name, final String[] args, final String run) {
        container().methods().put(name, new Method() {
            public Value run(Scope current, Value[] a) {
                if (a.length < args.length) {
                    throw new IllegalArgumentException(name + " does not have enough arguments (" + a.length + "/" + args.length + ")");
                }
                for (int x = 0; x < args.length; x++) {
                    storage().put(args[x], a[x]);
                }
                try {
                    GordianDefinedMethod.this.run(run);
                } catch (ValueReturned r) {
                    return r.value;
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new RuntimeException("Defined method (" + name + ") failed - " + e.getMessage());
                }
                return null;
            }
        });
    }
}
