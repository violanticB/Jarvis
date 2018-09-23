package controller.instance;

public interface InstanceInfo {
    String getName();
    String getPlugins();
    String getMaps();
    String getStartingMap();
    int getMaxPlayers();
    int getMemory();
    int getMaxInstances();
    int getStartingPort();

}
