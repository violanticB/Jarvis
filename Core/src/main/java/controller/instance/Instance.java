package controller.instance;

import java.util.List;
import java.util.UUID;

public interface Instance {
    String getName();
    int getId();

    Process getProcess();
    void setProcess(Process process);

    List<UUID> getPlayers();
}
