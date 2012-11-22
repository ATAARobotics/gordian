package edu.ata.script;

import java.util.Hashtable;

public class ReturningMethods {

    private static final Hashtable methods = new Hashtable();
    static {
        add(new ReturningMethod("exists") {
            public Data getValue(String[] args) {
                if (args.length == 0) {
                    return Data.getData("false");
                }
                for (int x = 0; x < args.length; x++) {
                    if (!VariableQueue.containsKey(args[x])) {
                        return Data.getData("false");
                    }
                }
                return Data.getData("true");
            }
        });
    }

    public static boolean contains(String methodName) {
        return methods.containsKey(methodName);
    }

    public static void add(ReturningMethod method) {
        methods.put(method.getName(), method);
    }

    public static Data get(String literal) {
        return ((ReturningMethod) methods.get(
                literal.substring(0, literal.indexOf("(")).trim()))
                .getValue(StringUtils.split(literal.substring(literal.indexOf("(") + 1,
                literal.lastIndexOf(")")), ','));
    }
}
