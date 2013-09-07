package edu.gordian.internal;

import edu.first.util.list.ArrayList;
import edu.first.util.list.Iterator;
import edu.first.util.list.List;
import language.instruction.Method;
import edu.gordian.scopes.GordianRuntime;
import language.internal.Methods;

public final class GordianMethods implements Methods {

    private final List nodes = new ArrayList();

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
