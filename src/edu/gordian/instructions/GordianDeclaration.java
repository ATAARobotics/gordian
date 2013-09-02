package edu.gordian.instructions;

import language.instruction.Declaration;
import language.value.Value;
import language.scope.Scope;

public class GordianDeclaration implements Declaration {

    private final Scope scope;

    public GordianDeclaration(Scope scope) {
        this.scope = scope;
    }

    public Value set(String key, Value val) {
        scope.storage().set(key, val);
        return val;
    }
}
