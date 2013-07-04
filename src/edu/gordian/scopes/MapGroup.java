package edu.gordian.scopes;

import edu.wpi.first.wpilibj.networktables2.util.List;
import java.util.Enumeration;
import java.util.Hashtable;

final class MapGroup {

    private final List maps = new List();

    public MapGroup(Hashtable start) {
        maps.add(start);
    }

    public MapGroup(MapGroup group) {
        for (int x = 0; x < group.maps.size(); x++) {
            maps.add(group.maps.get(x));
        }
    }

    public void add(Hashtable h) {
        maps.add(h);
    }

    public Enumeration keys() {
        return new Enumeration() {
            private final Enumeration[] enumerations;
            private int i;

            {
                enumerations = new Enumeration[maps.size()];
                for (int x = 0; x < maps.size(); x++) {
                    enumerations[x] = ((Hashtable) maps.get(x)).keys();
                }
            }

            public boolean hasMoreElements() {
                return enumerations[i].hasMoreElements()
                        || (++i < enumerations.length && enumerations[i].hasMoreElements());
            }

            public Object nextElement() {
                // needed to increment i
                return hasMoreElements() ? enumerations[i].nextElement() : null;
            }
        };
    }

    public Enumeration Elements() {
        return new Enumeration() {
            private final Enumeration[] enumerations;
            private int i;

            {
                enumerations = new Enumeration[maps.size()];
                for (int x = 0; x < maps.size(); x++) {
                    enumerations[x] = ((Hashtable) maps.get(x)).elements();
                }
            }

            public boolean hasMoreElements() {
                return enumerations[i].hasMoreElements()
                        || (++i < enumerations.length && enumerations[i].hasMoreElements());
            }

            public Object nextElement() {
                // needed to increment i
                return hasMoreElements() ? enumerations[i].nextElement() : null;
            }
        };
    }

    public boolean contains(Object value) {
        for (int x = 0; x < maps.size(); x++) {
            if (((Hashtable) maps.get(x)).contains(value)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsKey(Object key) {
        for (int x = 0; x < maps.size(); x++) {
            if (((Hashtable) maps.get(x)).containsKey(key)) {
                return true;
            }
        }
        return false;
    }

    public Object get(Object key) {
        for (int x = 0; x < maps.size(); x++) {
            if (((Hashtable) maps.get(x)).containsKey(key)) {
                return ((Hashtable) maps.get(x)).get(key);
            }
        }
        return null;
    }

    public Object put(Object key, Object value) {
        for (int x = 0; x < maps.size(); x++) {
            if (((Hashtable) maps.get(x)).containsKey(key)) {
                return ((Hashtable) maps.get(x)).put(key, value);
            }
        }
        return ((Hashtable) maps.get(maps.size() - 1)).put(key, value);
    }

    public Object remove(Object key) {
        // Nothing is ever removed - don't worry
        return null;
    }

    public void clear() {
    }

    public int size() {
        int s = 0;
        for (int x = 0; x < maps.size(); x++) {
            s += ((Hashtable) maps.get(x)).size();
        }
        return s;
    }

    public boolean isEmpty() {
        return size() == 0;
    }
}
