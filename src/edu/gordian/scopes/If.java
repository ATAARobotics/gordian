package edu.gordian.scopes;

import edu.gordian.Strings;
import edu.wpi.first.wpilibj.networktables2.util.List;

final class If extends Scope {

    public If(Scope scope) {
        super(scope);
    }

    public void run(String script) throws Exception {
        List cases = new List();
        List run = new List();
        if (Strings.contains(script, "if(")) {
            cases.add(script.substring(script.indexOf("if(") + 3, script.indexOf(");")));
            run.add(script.substring(script.indexOf(");") + 2,
                    script.indexOf("else") >= 0 ? script.indexOf("else") : script.length()));
            script = script.substring(script.indexOf("else") >= 0
                    ? script.indexOf("else") : script.length());
        }
        while (Strings.contains(script, "elseif(")) {
            cases.add(script.substring(script.indexOf("elseif(") + 7, script.indexOf(");")));
            run.add(script.substring(script.indexOf(");") + 2,
                    script.substring(6).indexOf("else") >= 0 ? script.substring(6).indexOf("else") + 6 : script.length()));
            script = script.substring(script.substring(6).indexOf("else") >= 0
                    ? script.substring(6).indexOf("else") + 6 : script.length());
        }
        if (Strings.contains(script, "else;")) {
            cases.add("true");
            run.add(script.substring(script.indexOf(";") + 1));
        }

        for (int x = 0; x < cases.size(); x++) {
            if (((Boolean) toValue((String) cases.get(x)).getValue()).booleanValue()) {
                super.run((String) run.get(x));
                return;
            }
        }
    }
}
