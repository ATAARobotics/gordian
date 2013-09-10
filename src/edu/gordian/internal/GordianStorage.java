package edu.gordian.internal;

import edu.first.util.list.ArrayList;
import edu.first.util.list.Iterator;
import edu.first.util.list.List;
import edu.gordian.scopes.GordianRuntime;
import edu.gordian.values.GordianNull;
import language.value.Value;
import language.internal.Storage;

public final class GordianStorage implements Storage {

    private final List nodes = new ArrayList();

    {
        set("null", GordianNull.get());
    }

    public GordianStorage() {
    }

    public GordianStorage(Storage s) {
        nodes.addAll(s.nodes());
    }

    public void clone(Storage s) {
        Iterator i = s.nodes().iterator();
        while (i.hasNext()) {
            Node o = (Node) i.next();
            nodes.add(new Node(o.key, o.val));
        }
    }

    public Value put(String key, Value value) {
        GordianRuntime.testName(key);
        Value old = get(key);
        nodes.add(new Node(key, value));
        return old;
    }

    public Value set(String key, Value value) {
        GordianRuntime.testName(key);
        Value old = get(key);
        if (old == null) {
            nodes.add(new Node(key, value));
        } else {
            for (int x = nodes.size() - 1; x >= 0; x--) {
                Node n = (Node) nodes.get(x);
                if (n.key.equals(key)) {
                    n.val = value;
                    return old;
                }
            }
        }
        return old;
    }

    public Value get(String key) {
        for (int x = nodes.size() - 1; x >= 0; x--) {
            Node n = (Node) nodes.get(x);
            if (n.key.equals(key)) {
                return n.val;
            }
        }
        return null;
    }

    public Value remove(String key) {
        for (int x = nodes.size() - 1; x >= 0; x--) {
            Node n = (Node) nodes.get(x);
            if (n.key.equals(key)) {
                nodes.remove(n);
                return n.val;
            }
        }
        return null;
    }

    public List nodes() {
        return nodes;
    }
}
