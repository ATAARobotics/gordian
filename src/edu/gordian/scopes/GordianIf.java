package edu.gordian.scopes;

import language.scope.Scope;
import edu.gordian.values.GordianBoolean;

public class GordianIf extends GordianScope {

    public GordianIf(Scope scope) {
        super(scope);
    }

    public void run(String cond, String run) {
        if (((GordianBoolean) getInterpreter().interpretValue(cond)).get()) {
            run(run);
        }
    }
}
