package controller.instance.spigot;

import controller.instance.Instance;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ethan on 9/24/2018.
 */
public class SpigotInstance implements Instance {

    private String type;
    private int id;
    private int port;

    public SpigotInstance(String type, int id, int port) {
        this.type = type;
        this.id = id;
        this.port = port;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String getName() {
        return type + "-" + id;
    }

    @Override
    public int getId() {
        return id;
    }

    public int getPort() {
        return port;
    }

    @Override
    public String toString() {
        return getName() + " : port " + port;
    }

}
