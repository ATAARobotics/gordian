package language.scope;

import language.element.Analyser;
import language.internal.Methods;
import language.internal.Storage;
import language.value.Interpreter;

public interface Scope {

    public Scope container();

    public Methods methods();

    public Storage storage();

    public void run(String i);

    public Analyser getAnalyser();

    public Interpreter getInterpreter();
}
