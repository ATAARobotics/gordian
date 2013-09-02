package language.element;

import language.value.Interpreter;
import language.value.Value;

public interface Element {

    public void analyse(Analyser a);

    public Value interpret(Interpreter i);
}
