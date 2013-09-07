package language.internal;

import edu.first.util.list.List;
import language.instruction.Method;

public interface Methods {

    public void clone(Methods m);

    public void put(String key, Method method);

    public Method get(String key);

    public List nodes();

    public static final class Node {

        public final String key;
        public final Method val;

        public Node(String key, Method val) {
            this.key = key;
            this.val = val;
        }
    }
}
