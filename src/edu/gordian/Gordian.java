package edu.gordian;

import edu.gordian.scopes.Scope;
import edu.gordian.elements.methods.UserMethod;
import edu.gordian.values.UserReturningMethod;

public final class Gordian {

    private Gordian() {
    }

    public static void run(UserMethod[] methods, UserReturningMethod[] returning, String script) throws Exception {
        new Scope(methods, returning).run(script);
    }
    
    public static void run(UserMethod[] methods, String script) throws Exception {
        run(methods, new UserReturningMethod[0], script);
    }
    
    public static void run(String script) throws Exception {
        new Scope().run(script);
    }
}
