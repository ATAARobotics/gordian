package edu.gordian.scopes;

import edu.first.util.Strings;
import edu.first.util.list.ArrayList;
import edu.first.util.list.Iterator;
import edu.first.util.list.List;
import edu.gordian.values.GordianBoolean;
import language.scope.Scope;

public class GordianIf extends GordianScope {

    private List conditions = new ArrayList();
    private List instructions = new ArrayList();

    public static void run(Scope scope, String s) {
        GordianIf i = new GordianIf(scope);
        build(i, s);

        i.run();
    }

    private static void build(GordianIf i, String s) {
        if (s.startsWith("if")) {
            i.addIf(s.substring(3, s.substring(0, s.indexOf(":")).lastIndexOf(')')),
                    s.substring(s.indexOf(";") + 1, nextToken(s.substring(3)) + 3));
            build(i, s.substring(nextToken(s.substring(3)) + 3));
        } else if (s.startsWith("elseif")) {
            i.addIf(s.substring(7, s.substring(0, s.indexOf(";")).lastIndexOf(')')),
                    s.substring(s.indexOf(";") + 1, nextToken(s.substring(7)) + 7));
            build(i, s.substring(nextToken(s.substring(7)) + 7));
        } else if (s.startsWith("else;")) {
            i.addElse(s.substring(5));
        }
    }

    private static int nextToken(String s) {
        if (Strings.contains(s, ";if")) {
            return s.indexOf(";if") + 1;
        } else if (Strings.contains(s, "elseif")) {
            return s.indexOf("elseif");
        } else if (Strings.contains(s, "else;")) {
            return s.indexOf("else;");
        } else {
            return -1;
        }
    }

    public GordianIf(Scope scope) {
        super(scope);
    }

    public void addIf(String condition, String instr) {
        conditions.add(condition);
        instructions.add(instr);
    }

    public void addElse(String instr) {
        conditions.add("true");
        instructions.add(instr);
    }

    public void run() {
        Iterator i = conditions.iterator();
        Iterator x = instructions.iterator();
        while (i.hasNext()) {
            String cond = (String) i.next();
            String inst = (String) x.next();
            if (((GordianBoolean) getInterpreter().interpretValue(cond)).get()) {
                run(inst);
                return;
            }
        }
    }
}