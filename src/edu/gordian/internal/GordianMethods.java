package edu.gordian.internal;

import edu.first.util.list.ArrayList;
import edu.first.util.list.Collections;
import edu.first.util.list.Iterator;
import edu.first.util.list.List;
import language.instruction.Method;
import edu.gordian.scopes.GordianRuntime;
import edu.gordian.values.GordianBoolean;
import edu.gordian.values.GordianNumber;
import edu.gordian.values.GordianString;
import java.util.Random;
import language.internal.Methods;
import language.scope.Scope;
import language.value.Value;

public final class GordianMethods implements Methods {

    private final List nodes = new ArrayList();
    private static final Random RANDOM = new Random();
    
    {
        put("return", new Method() {
            public Value run(Scope current, Value[] args) {
                if (args.length > 0) {
                    throw new ValueReturned(args[0]);
                } else {
                    throw new ValueReturned(null);
                }
            }
        });
        put("break", new Method() {
            public Value run(Scope current, Value[] args) {
                throw new ScopeBreak();
            }
        });
        put("delete", new Method() {
            public Value run(Scope current, Value[] args) {
                return current.storage().remove(((GordianString) args[0]).toString());
            }
        });
        put("int", new Method() {
            public Value run(Scope current, Value[] args) {
                if (args[0] instanceof GordianNumber) {
                    return new GordianNumber(((GordianNumber) args[0]).getInt());
                } else {
                    return new GordianNumber(GordianNumber.toNumber(args[0].toString()).getInt());
                }
            }
        });
        put("num", new Method() {
            public Value run(Scope current, Value[] args) {
                if (args[0] instanceof GordianNumber) {
                    return new GordianNumber(((GordianNumber) args[0]).getDouble());
                } else {
                    return GordianNumber.toNumber(args[0].toString());
                }
            }
        });
        put("bool", new Method() {
            public Value run(Scope current, Value[] args) {
                if (args[0] instanceof GordianBoolean) {
                    return new GordianBoolean(((GordianBoolean) args[0]).get());
                } else {
                    return GordianBoolean.toBoolean(args[0].toString());
                }
            }
        });
        put("str", new Method() {
            public Value run(Scope current, Value[] args) {
                return new GordianString(args[0].toString());
            }
        });
        put("print", new Method() {
            public Value run(Scope current, Value[] args) {
                if (args.length > 1) {
                    System.out.println(Collections.asList(args));
                } else {
                    System.out.println(args[0]);
                }
                return null;
            }
        });
        put("sleep", new Method() {
            public Value run(Scope current, Value[] args) {
                try {
                    Thread.sleep(((GordianNumber) args[0]).getLong());
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                return null;
            }
        });
        put("rand", new Method() {
            public Value run(Scope current, Value[] args) {
                return new GordianNumber(RANDOM.nextDouble());
            }
        });
        put("randint", new Method() {
            public Value run(Scope current, Value[] args) {
                return new GordianNumber(RANDOM.nextInt());
            }
        });
        put("neg", new Method() {
            public Value run(Scope current, Value[] args) {
                return new GordianNumber(-((GordianNumber) args[0]).getDouble());
            }
        });
    }

    public GordianMethods() {
    }

    public GordianMethods(Methods s) {
        nodes.addAll(s.nodes());
    }

    public void clone(Methods m) {
        Iterator i = m.nodes().iterator();
        while (i.hasNext()) {
            Node o = (Node) i.next();
            nodes.add(new Node(o.key, o.val));
        }
    }

    public void put(String key, Method method) {
        GordianRuntime.testName(key);
        nodes.add(new Node(key, method));
    }

    public Method get(String key) {
        for (int x = nodes.size() - 1; x >= 0; x--) {
            Node n = (Node) nodes.get(x);
            if (n.key.equals(key)) {
                return n.val;
            }
        }
        return null;
    }

    public List nodes() {
        return nodes;
    }
}
