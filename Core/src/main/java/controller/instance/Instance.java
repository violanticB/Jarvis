package controller.instance;

import java.util.List;
import java.util.UUID;

public interface Instance {
    String getName();
    int getId();

    List<UUID> getPlayers();
}
