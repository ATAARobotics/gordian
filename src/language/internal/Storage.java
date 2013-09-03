package language.internal;

import edu.first.util.list.List;
import language.value.Value;

public interface Storage {

    public Value put(String key, Value value);

    public Value set(String key, Value value);

    public Value get(String key);

    public Value remove(String key);

    public List nodes();
}
