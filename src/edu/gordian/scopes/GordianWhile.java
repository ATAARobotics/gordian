package edu.gordian.scopes;

import language.scope.Scope;
import edu.gordian.values.GordianBoolean;

public class GordianWhile extends GordianScope {

    public GordianWhile(Scope scope) {
        super(scope);
    }

    public void run(String cond, String run) {
        while (((GordianBoolean) getInterpreter().interpretValue(cond)).get()) {
            run(run);
        }
    }
}
