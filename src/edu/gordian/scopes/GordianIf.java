package edu.gordian.scopes;

import language.scope.Scope;
import edu.gordian.values.GordianBoolean;

public class GordianIf extends GordianScope {

    public static void run(Scope scope, String s) {
        new GordianIf(scope).run(s.substring(3, s.substring(0, s.indexOf(":")).lastIndexOf(')')),
                s.substring(s.indexOf(";") + 1));
    }

    public GordianIf(Scope scope) {
        super(scope);
    }

    public void run(String cond, String run) {
        if (((GordianBoolean) getInterpreter().interpretValue(cond)).get()) {
            run(run);
        }
    }
}
