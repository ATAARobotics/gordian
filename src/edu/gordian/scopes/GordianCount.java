package edu.gordian.scopes;

import language.scope.Scope;
import edu.gordian.values.GordianNumber;

public class GordianCount extends GordianScope {

    public static void run(Scope scope, String s) {
        new GordianCount(scope).run(s.substring(6, s.indexOf(",")),
                s.substring(s.indexOf(",") + 1, s.substring(0, s.indexOf(":")).lastIndexOf(',')),
                s.substring(s.substring(0, s.indexOf(":")).lastIndexOf(',') + 1, s.substring(0, s.indexOf(":")).lastIndexOf(')')),
                s.substring(s.indexOf(";") + 1));
    }

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
