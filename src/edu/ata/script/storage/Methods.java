package edu.ata.script.storage;

import edu.ata.script.Data;
import edu.ata.script.instructions.MethodBody;

/**
 * @author Joel Gallant
 */
public class Methods extends Storage {

    public static final Methods METHODS_STORAGE = new Methods();

    static {
        METHODS_STORAGE.addMethod("print", new MethodBody() {
            public void run(Data[] args) {
                String s = "";
                for (int x = 0; x < args.length; x++) {
                    s += args[x].getValue();
                }
                System.out.println(s);
            }
        });
        METHODS_STORAGE.addMethod("wait", new MethodBody() {
            public void run(Data[] args) {
                double t = 0;
                if (args[0] instanceof edu.ata.script.data.Double) {
                    t = ((edu.ata.script.data.Double) args[0]).get();
                } else if (args[0] instanceof edu.ata.script.data.Integer) {
                    t = ((edu.ata.script.data.Integer) args[0]).get();
                }
                if (t != 0) {
                    try {
                        Thread.sleep((long) (t * 1e3));
                    } catch (InterruptedException e) {
                    }
                }
            }
        });
    }

    protected void add(String key, Object value) {
        if (!(value instanceof MethodBody)) {
            throw new RuntimeException("Added a method that WAS NOT A METHOD - " + key);
        } else {
            getStorage().put(key, value);
        }
    }

    public void addMethod(String name, MethodBody method) {
        add(name, method);
    }
}
