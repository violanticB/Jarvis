package controller.instance;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ethan on 9/24/2018.
 */
public class SpigotInstance implements Instance {

    private String type;
    private int id;
    private Process process;
    private List<UUID> players;

    public SpigotInstance(String type, int id, Process process) {
        this.type = type;
        this.id = id;
        this.process = process;
        this.players = new ArrayList<>();
    }

    @Override
    public String getName() {
        return type + "-" + id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public Process getProcess() {
        return process;
    }

    @Override
    public void setProcess(Process process) {
        this.process = process;
    }

    @Override
    public List<UUID> getPlayers() {
        return players;
    }
}
