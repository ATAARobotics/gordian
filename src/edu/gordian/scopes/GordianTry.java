package edu.gordian.scopes;

import language.scope.Scope;

public class GordianTry extends GordianScope {

    public static void run(Scope scope, String s) {
        new GordianTry(scope).run(s.substring(s.indexOf(";") + 1));
    }

    public GordianTry(Scope scope) {
        super(scope);
    }

    public void run(String i) {
        try {
            super.run(i);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
