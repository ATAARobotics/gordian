package edu.gordian.scopes;

import language.scope.Scope;
import edu.gordian.values.GordianBoolean;

public class GordianWhile extends GordianScope {

    public static void run(Scope scope, String s) {
        new GordianWhile(scope).run(s.substring(6, s.substring(0, s.indexOf(":")).lastIndexOf(')')),
                s.substring(s.indexOf(";") + 1));
    }

    public GordianWhile(Scope scope) {
        super(scope);
    }

    public void run(String cond, String run) {
        while (((GordianBoolean) getInterpreter().interpretValue(cond)).get()) {
            run(run);
        }
    }
}
