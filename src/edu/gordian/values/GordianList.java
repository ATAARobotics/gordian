package edu.gordian.values;

import edu.first.util.list.ArrayList;
import edu.first.util.list.List;
import edu.gordian.scopes.GordianScope;
import language.instruction.Method;
import language.scope.Scope;
import language.value.Value;

public class GordianList extends GordianScope implements Value {

    private final List list = new ArrayList();

    public GordianList(Scope s) {
        super(s);
        methods().put("get", new Method() {
            public Value run(Scope current, Value[] args) {
                return (Value) list.get(((GordianNumber) args[0]).getInt());
            }
        });
        methods().put("add", new Method() {
            public Value run(Scope current, Value[] args) {
                list.add(args[0]);
                return args[0];
            }
        });
        methods().put("addAll", new Method() {
            public Value run(Scope current, Value[] args) {
                list.addAll(((GordianList) args[0]).list);
                return null;
            }
        });
        methods().put("set", new Method() {
            public Value run(Scope current, Value[] args) {
                list.set(((GordianNumber) args[0]).getInt(), args[1]);
                return args[1];
            }
        });
        methods().put("clear", new Method() {
            public Value run(Scope current, Value[] args) {
                list.clear();
                return null;
            }
        });
        methods().put("remove", new Method() {
            public Value run(Scope current, Value[] args) {
                list.remove(args[0]);
                return args[0];
            }
        });
        methods().put("removeat", new Method() {
            public Value run(Scope current, Value[] args) {
                return (Value) list.remove(((GordianNumber) args[0]).getInt());
            }
        });
        methods().put("indexof", new Method() {
            public Value run(Scope current, Value[] args) {
                return new GordianNumber(list.indexOf(args[0]));
            }
        });
        methods().put("size", new Method() {
            public Value run(Scope current, Value[] args) {
                return new GordianNumber(list.size());
            }
        });
        methods().put("contains", new Method() {
            public Value run(Scope current, Value[] args) {
                return new GordianBoolean(list.contains(args[0]));
            }
        });
    }

    public GordianList() {
        this(null);
    }

    public Value add(Value v) {
        list.add(v);
        return v;
    }

    public Value set(int index, Value v) {
        list.set(index, v);
        return v;
    }

    public Value get(int index) {
        return (Value) list.get(index);
    }

    public String toString() {
        return list.toString();
    }

    public boolean equals(Object obj) {
        if (obj instanceof GordianList) {
            return list.equals(((GordianList) obj).list);
        } else {
            return false;
        }
    }
}
