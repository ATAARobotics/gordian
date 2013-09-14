package edu.gordian;

import edu.gordian.internal.GordianMethods;
import edu.gordian.internal.GordianStorage;
import edu.gordian.values.GordianInstanceFactory;
import language.internal.Methods;
import language.internal.Storage;
import language.scope.Instance;

public class Class implements language.scope.ClassGenerator {

    private final Storage storage = new GordianStorage();
    private final Methods methods = new GordianMethods();

    public Class(Variable[] v, Method[] m) {
        for (int x = 0; x < v.length; x++) {
            storage.put(v[x].getName(), v[x].getValue());
        }
        for (int x = 0; x < m.length; x++) {
            methods.put(m[x].getName(), m[x]);
        }
    }

    public Class(Variable[] v) {
        this(v, new Method[0]);
    }

    public Class(Method[] m) {
        this(new Variable[0], m);
    }

    public Instance construct() {
        return new GordianInstanceFactory().cloneMethods(methods).cloneStorage(storage).toInstance();
    }
}
