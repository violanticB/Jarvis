package controller.instance;

public interface InstanceInfo {

    /**
     * Name of instance image
     * @return name
     */
    String getName();

    /**
     * Plugins directory path
     * @return map directory
     */
    String getPlugins();

    /**
     * Maps directory path
     * @return map directory
     */
    String getMaps();

    /**
     * Map that will deploy with server
     * @return map
     */
    String currentMap();

    /**
     * Properties directory path
     * This includes things such as server.properties,
     * server.sh, and stop.sh
     *
     * @return properties directory
     */
    String getProperties();

    /**
     * Version of server implementation
     * @return version
     */
    String getVersion();

    /**
     * Max instances allowed for the type
     * @return max instances
     */
    int getMaxInstances();

    /**
     * Starting port of port range for instances of this type
     * @return starting port
     */
    int getStartingPort();

    /**
     * Returns true if the map is copied every startup
     * @return map refresh
     */
    boolean mapRefresh();
}
