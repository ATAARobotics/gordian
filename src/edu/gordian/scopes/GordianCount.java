package edu.gordian.scopes;

import language.scope.Scope;
import edu.gordian.values.GordianNumber;

public class GordianCount extends GordianScope {

    public GordianCount(Scope scope) {
        super(scope);
    }

    public void run(String name, String from, String to, String run) {
        for (int x = ((GordianNumber) getInterpreter().interpretValue(from)).getInt();
                x <= ((GordianNumber) getInterpreter().interpretValue(to)).getInt(); x++) {
            storage().set(name, new GordianNumber(x));
            run(run);
        }
    }
}
