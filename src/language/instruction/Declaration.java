package language.instruction;

import language.value.Value;

public interface Declaration {

    public Value set(String key, Value val);
}
