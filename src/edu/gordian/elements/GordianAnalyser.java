package edu.gordian.elements;

import edu.first.util.Strings;
import edu.gordian.internal.ScopeBreak;
import edu.gordian.scopes.GordianIf;
import edu.gordian.scopes.GordianWhile;
import edu.gordian.scopes.GordianFor;
import language.element.Analyser;
import language.scope.Scope;
import edu.gordian.scopes.GordianCount;
import edu.gordian.scopes.GordianDefinedMethod;
import edu.gordian.scopes.GordianScope;
import edu.gordian.scopes.GordianThread;
import edu.gordian.scopes.GordianTry;
import edu.gordian.values.GordianClass;

public class GordianAnalyser implements Analyser {

    private final Scope scope;

    public GordianAnalyser(Scope scope) {
        this.scope = scope;
    }

    public void analyseBlock(String s) {
        try {
            if (s.startsWith("if")) {
                new GordianIf(scope).run(s.substring(3, s.substring(0, s.indexOf(":")).lastIndexOf(')')),
                        s.substring(s.indexOf(";") + 1));
            } else if (s.startsWith("while")) {
                new GordianWhile(scope).run(s.substring(6, s.substring(0, s.indexOf(":")).lastIndexOf(')')),
                        s.substring(s.indexOf(";") + 1));
            } else if (s.startsWith("for")) {
                new GordianFor(scope).run(s.substring(4, s.substring(0, s.indexOf(":")).lastIndexOf(')')),
                        s.substring(s.indexOf(";") + 1));
            } else if (s.startsWith("count")) {
                new GordianCount(scope).run(s.substring(6, s.indexOf(",")),
                        s.substring(s.indexOf(",") + 1, s.substring(0, s.indexOf(":")).lastIndexOf(',')),
                        s.substring(s.substring(0, s.indexOf(":")).lastIndexOf(',') + 1, s.substring(0, s.indexOf(":")).lastIndexOf(')')),
                        s.substring(s.indexOf(";") + 1));
            } else if (s.startsWith("thread")) {
                new GordianThread(scope).runThread(s.substring(s.indexOf(";") + 1));
            } else if (s.startsWith("try")) {
                new GordianTry(scope).run(s.substring(s.indexOf(";") + 1));
            } else if (s.startsWith("def")) {
                new GordianDefinedMethod(scope).define(s.substring(3, s.indexOf("(")),
                        Strings.split(s.substring(s.indexOf("(") + 1, s.substring(0, s.indexOf(";")).lastIndexOf(')')), ","),
                        s.substring(s.indexOf(";") + 1));
            } else if (s.startsWith("scope")) {
                new GordianScope(scope).run(s.substring(s.indexOf(";") + 1));
            } else if (s.startsWith("class")) {
                scope.storage().put(s.substring(5, s.indexOf(":")), new GordianClass(scope, s.substring(s.indexOf(";") + 1)));
            } else {
                throw new NullPointerException("The value \"" + s + "\" could not be interpreted as a block.");
            }
        } catch (ScopeBreak ex) {
            // Block was broken
        }
    }

    public void analyseInstruction(String s) {
        try {
            // Ask for value - all instructions have a value.
            scope.getInterpreter().interpretValue(s);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            throw new NullPointerException("The instruction \"" + s + "\" could not be completed.");
        }
    }
}