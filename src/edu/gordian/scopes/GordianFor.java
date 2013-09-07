package edu.gordian.scopes;

import language.scope.Scope;
import edu.gordian.values.GordianNumber;

public class GordianFor extends GordianScope {

    public static void run(Scope scope, String s) {
        new GordianFor(scope).run(s.substring(4, s.substring(0, s.indexOf(":")).lastIndexOf(')')),
                s.substring(s.indexOf(";") + 1));
    }

    public GordianFor(Scope scope) {
        super(scope);
    }

    public void run(String i, String run) {
        for (int x = 0; x < ((GordianNumber) getInterpreter().interpretValue(i)).getInt(); x++) {
            run(run);
        }
    }
}
