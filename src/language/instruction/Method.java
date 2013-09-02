package language.instruction;

import language.value.Value;

public interface Method {

    public Value run(Value[] args);
}
