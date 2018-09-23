package controller;

import controller.instance.InstanceManager;

public class Jarvis {

    public static Jarvis instance;
    private static InstanceManager instanceManager;

    private Jarvis() {
        instanceManager = new InstanceManager(this);
    }

    public InstanceManager getInstanceManager() {
        return instanceManager;
    }

    public static Jarvis getInstance() {
        return instance;
    }

    public static void main(String[] args) {
        instance = new Jarvis();
        instance.getInstanceManager().loadTypes();
    }

}
