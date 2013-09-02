package edu.gordian.scopes;

import language.scope.Scope;

public class GordianThread extends GordianScope {

    public GordianThread(Scope scope) {
        super(scope);
    }

    public void runThread(final String run) {
        new Thread(new Runnable() {
                    public void run() {
                GordianThread.this.run(run);
            }
        }).start();
    }
}
