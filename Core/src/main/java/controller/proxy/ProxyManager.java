package controller.proxy;

import controller.Jarvis;

/**
 * Created by Ethan on 9/25/2018.
 */
public class ProxyManager {

    private Jarvis instance;

    public ProxyManager(Jarvis instance) {
        this.instance = instance;
    }

    public Jarvis getInstance() {
        return instance;
    }

    private void startProxy() {

    }
}
