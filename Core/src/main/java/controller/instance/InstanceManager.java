package controller.instance;

import controller.Jarvis;
import controller.instance.spigot.SpigotInstance;
import controller.instance.spigot.SpigotInstanceInfo;
import controller.util.FileUtil;
import controller.util.JsonUtil;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InstanceManager {

    private Jarvis instance;
    private String SPIGOT_PATH;
    private String INSTANCES_HOME;
    private String APP_HOME;

    private Set<InstanceInfo> instanceTypes;
    private Map<String, List<Instance>> instanceMap;

    /**
     * Controls active instances on the network
     * @param instance Jarvis instance
     */
    public InstanceManager(Jarvis instance) {
        this.instance = instance;
        this.instanceTypes = new HashSet<>();
        this.instanceMap = new ConcurrentHashMap<>();
    }

    /**
     * Jarvis instance
     * @return
     */
    public Jarvis getInstance() {
        return instance;
    }

    /**
     * Instance images
     * @return Set of {@link InstanceInfo}
     */
    public Set<InstanceInfo> getInstanceTypes() {
        return instanceTypes;
    }

    /**
     * Get an instance image by name
     * @return image
     */
    public InstanceInfo getImage(String name) {
        for (InstanceInfo instanceType : instanceTypes) {
            if(instanceType.getName().equalsIgnoreCase(name)) {
                return instanceType;
            }
        }

        return null;
    }

    /**
     * List of Instances by type
     * @return Map of instances sorted by type
     */
    public Map<String, List<Instance>> getInstances() {
        return instanceMap;
    }

    /**
     * Check if there is an instance by a given name
     * @param type Instance type
     * @param name Instance name
     * @return contains
     */
    public boolean contains(String type, String name) {
        for (Instance i : instanceMap.get(type)) {
            if(i.getName().equalsIgnoreCase(name)) {
                return true;
            }

        }

        return false;
    }

    /**
     * Load server images from SQL
     */
    public void loadTypes() {
        String query = "SELECT * FROM server_type;";
        try {
            PreparedStatement statement = this.instance.getSqlManager().getConnection().prepareStatement(query);
            ResultSet set = statement.executeQuery();
            while(set.next()) {
                SpigotInstanceInfo image = new SpigotInstanceInfo(set);
                instanceTypes.add(image);
                instanceMap.put(image.getName(), new ArrayList<>());
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

//        JSONObject json = JsonUtil.parseFile("instances/config.json");
//        if(json == null) {
//            return;
//        }
//
//        json = (JSONObject) json.get("types");
//
//        JSONObject finalJson = json;
//        for (Object key : json.keySet()) {
//            JSONObject object = (JSONObject) finalJson.get(key);
//            getInstanceTypes().add(new SpigotInstanceInfo(object));
//        }

        Jarvis.log("Registering instance types...");
        getInstanceTypes().forEach((i) -> {
            getInstances().put(i.getName(), new ArrayList<>());
            Jarvis.log("Registered: [" + i.getName().toUpperCase() + "]");
            System.out.println(i.toString());
        });
    }

    /**
     * Check if a given string is a valid server type name.
     * @param name Given name
     * @return isType
     */
    public boolean isType(String name) {
        boolean isType = false;

        for (InstanceInfo instanceType : instanceTypes) {
            if(instanceType.getName().equalsIgnoreCase(name)) {
                isType = true;
            }
        }

        return isType;
    }

    /**
     * Loads settings for Instance Manager, and application
     * in general.
     */
    public void loadSettings() {
        JSONObject object = JsonUtil.parseFile("config.json");
        JSONObject instanceSettings = (JSONObject) object.get("instances");

        String instanceDir = (String) instanceSettings.get("home-location");
        INSTANCES_HOME = instanceDir;

        JSONObject spigotSettings = (JSONObject) object.get("spigot");
        String fileDir = (String) spigotSettings.get("build-location");
        SPIGOT_PATH = fileDir;

        JSONObject appHome = (JSONObject) object.get("app");
        APP_HOME = (String) appHome.get("app-home");

        Jarvis.log("Registering network settings...");
        Jarvis.log("[Instance Settings] ->");
        Jarvis.log("- home-location : " + instanceDir);
        Jarvis.log("[Spigot Settings] -> ");
        Jarvis.log("- build-directory : " + fileDir);
    }

    /**
     * Create and start a server instance by
     * any given server type
     * @param type Server Type
     */
    public void createInstance(String type) {
        InstanceInfo info = getImage(type);
        int startingPort = info.getStartingPort();
        int maxInstances = info.getMaxInstances();
        int currentInstances = instanceMap.get(type).size();

        int remaining = maxInstances - currentInstances;
        int id = (maxInstances - remaining) + 1;
        int nextPort = (startingPort + id) - 1;
        Jarvis.log("Starting instance " + info.getName().toUpperCase() + "-" + id + " @ localhost:" + nextPort);

        String instanceName = type + "-" + id;

        File plugins = new File(info.getPlugins());
        File properties = new File(info.getProperties());

        String version = info.getVersion();
        String spigot = SPIGOT_PATH + "/" + version;
        File spigotFile = new File(spigot);

        File instanceDir = new File(INSTANCES_HOME + "/" + info.getName().toUpperCase() + "-" + id);
        if(!instanceDir.exists()) {
            instanceDir.mkdirs();
        }

        File maps = new File(info.getMaps());
        File currentMap = new File(instanceDir, info.currentMap());

        try {

            if(info.mapRefresh() || !currentMap.exists()) {
                FileUtils.copyDirectory(maps, instanceDir);
            }

            FileUtils.copyDirectory(plugins, new File(instanceDir, "plugins"));
            FileUtils.copyDirectory(properties, instanceDir);
            FileUtils.copyDirectory(spigotFile, instanceDir);

            // server.properties file
            File propertiesFile = new File(instanceDir, "server.properties");
            FileUtil.replaceFile(propertiesFile, "{port}", "" + nextPort);
            FileUtil.replaceFile(propertiesFile, "{map}", info.currentMap());

            // Stop script for instance
            File stopScript = new File(instanceDir, "stop.sh");
            FileUtil.replaceFile(stopScript, "{instance}", instanceName);

            // Start script for instance
            File startScript = new File(instanceDir, "server.sh");
            FileUtil.replaceFile(startScript, "{instance}", instanceName);
            String scriptLocation = instanceDir.getPath() + "/server.sh";
            Jarvis.log("Attempting to start script '" + scriptLocation + "'");

            ProcessBuilder screenBuilder = new ProcessBuilder("sh", scriptLocation);
            screenBuilder.directory(instanceDir);
            screenBuilder.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        Instance instance = new SpigotInstance(info.getName(), id, nextPort);
        getInstances().get(info.getName()).add(instance);

        // Total output should look something like 'instance setup LOBBY 1 2000'
        getInstance().getRedis().getPub().send("bungee", "instance setup " + instance.getName() + " " + nextPort);
    }

    /**
     * Runs process that kills instances
     * @param instance Server instance
     */
    public void killScreen(Instance instance) {
        String name = instance.getName();
        ProcessBuilder stopProcess = new ProcessBuilder("java", "-jar", "jarvis-worker.jar", name);
        stopProcess.directory(new File(APP_HOME));

        try {
            stopProcess.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
