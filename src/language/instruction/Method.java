package language.instruction;

import language.scope.Scope;
import language.value.Value;

public interface Method {

    public Value run(Scope current, Value[] args);
}
