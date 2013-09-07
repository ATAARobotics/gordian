package language.internal;

import edu.first.util.list.List;
import language.value.Value;

public interface Storage {

    public void clone(Storage s);

    public Value put(String key, Value value);

    public Value set(String key, Value value);

    public Value get(String key);

    public Value remove(String key);

    public List nodes();

    public static final class Node {

        public final String key;
        public Value val;

        public Node(String key, Value val) {
            this.key = key;
            this.val = val;
        }
    }
}
