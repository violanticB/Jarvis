package controller.instance.spigot;

import controller.instance.InstanceInfo;
import org.json.simple.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpigotInstanceInfo implements InstanceInfo {

    private final String name;
    private final String pluginsDirectory;
    private final String mapsDirectory;
    private final String currentMap;
    private final String properties;
    private final String version;
    private final int maxPlayers;
    private final int maxInstances;
    private final int startingPort;
    private boolean mapRefresh;

    public SpigotInstanceInfo(JSONObject object) {
        this.name = object.get("name").toString();
        this.pluginsDirectory = object.get("plugin-location").toString();
        this.mapsDirectory = object.get("maps-location").toString();
        this.currentMap = object.get("current-map").toString();
        this.properties = object.get("properties").toString();
        this.version = object.get("spigot-version").toString();
        this.maxPlayers = Integer.parseInt(object.get("max-players").toString());
        this.maxInstances = Integer.parseInt(object.get("max-instances").toString());
        this.startingPort = Integer.parseInt(object.get("starting-port").toString());
    }

    public SpigotInstanceInfo(ResultSet set) throws SQLException {
        this.name = set.getString("name");
        this.pluginsDirectory = set.getString("plugin_location");
        this.mapsDirectory = set.getString("maps_location");
        this.currentMap = set.getString("current_map");
        this.properties = set.getString("properties");
        this.version = set.getString("spigot_version");
        this.maxPlayers = set.getInt("max_players");
        this.maxInstances = set.getInt("max_instances");
        this.startingPort = set.getInt("starting_port");
        this.mapRefresh = set.getBoolean("map_refresh");
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPlugins() {
        return this.pluginsDirectory;
    }

    @Override
    public String getMaps() {
        return this.mapsDirectory;
    }

    @Override
    public String currentMap() {
        return currentMap;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public int getMaxInstances() {
        return maxInstances;
    }

    @Override
    public int getStartingPort() {
        return startingPort;
    }

    @Override
    public String getProperties() {
        return properties;
    }

    @Override
    public boolean mapRefresh() {
        return mapRefresh;
    }

    @Override
    public String toString() {
        return "\n          name = '" + this.name + "'"
                + "\n          max-players = '" + maxPlayers + "'"
                + "\n          max-instances = '" + maxInstances + "'"
                + "\n          current-map = '" + currentMap + "'"
                + "\n          starting-port = '" + startingPort + "'"
                + "\n          maps-directory = " + mapsDirectory + ""
                + "\n          plugins-directory = " + pluginsDirectory + "\n";
    }
}
