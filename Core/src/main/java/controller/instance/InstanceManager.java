package controller.instance;

import controller.Jarvis;
import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InstanceManager {
    private Jarvis instance;

    private String SPIGOT_PATH;
    private String SPIGOT_NAME;

    private String INSTANCES_HOME;

    private Map<String, Integer> instanceCount;
    private Map<String, InstanceInfo> instanceTypes;
    private Map<String, Map<Integer, Instance>> instanceMap;

    public InstanceManager(Jarvis instance) {
        this.instance = instance;
        this.instanceCount = new ConcurrentHashMap<>();
        this.instanceTypes = new ConcurrentHashMap<>();
        this.instanceMap = new ConcurrentHashMap<>();
    }

    public Jarvis getInstance() {
        return instance;
    }

    public Map<String, Integer> getInstanceCount() {
        return instanceCount;
    }

    public Map<String, InstanceInfo> getInstanceTypes() {
        return instanceTypes;
    }

    public Map<String, Map<Integer, Instance>> getInstances() {
        return instanceMap;
    }

    public void loadTypes() {
        JSONObject json = parseFile("instances/config.json");
        json = (JSONObject) json.get("types");

        JSONObject finalJson = json;
        json.keySet().forEach((k) -> {
            JSONObject object = (JSONObject) finalJson.get(k);

            getInstanceCount().put(object.get("name").toString(), 0);
            getInstanceTypes().put(object.get("name").toString(), new InstanceInfo() {
                @Override
                public String getName() {
                    return object.get("name").toString();
                }

                @Override
                public String getPlugins() {
                    return object.get("plugin-location").toString();
                }

                @Override
                public String getMaps() {
                    return object.get("maps-location").toString();
                }

                @Override
                public String getStartingMap() {
                    return object.get("starting-map").toString();
                }

                @Override
                public int getMaxPlayers() {
                    return Integer.valueOf(object.get("max-players").toString());
                }

                @Override
                public int getMemory() {
                    return Integer.valueOf(object.get("memory").toString());
                }

                @Override
                public int getMaxInstances() {
                    return Integer.valueOf(object.get("max-instances").toString());
                }

                @Override
                public int getStartingPort() {
                    return Integer.valueOf(object.get("starting-port").toString());
                }

                @Override
                public String toString() {
                    return "name=" + getName() + ",plugins=" + getPlugins() + ",maps=" + getMaps() + ",defmap=" + getStartingMap() + ",maxPlayers=" + getMaxPlayers() + ",memory=" + getMemory() + ",max-instances=" + getMaxInstances() + ",starting-port=" + getStartingPort();
                }
            });
        });

        System.out.println("----- JARVIS -----");
        System.out.println("Registering instance types...");
        getInstanceTypes().forEach((k, v) -> {
            getInstances().put(k, new ConcurrentHashMap<>());
            System.out.println("Registered: " + k);
            System.out.println("     -> " + v.toString());
        });
        System.out.println("------------------");
    }

    public boolean isType(String name) {
        return getInstanceTypes().containsKey(name);
    }

    public void loadSettings() {
        JSONObject object = parseFile("config.json");
        JSONObject instanceSettings = (JSONObject) object.get("instances");

        String instanceDir = (String) instanceSettings.get("home-location");
        INSTANCES_HOME = instanceDir;

        JSONObject spigotSettings = (JSONObject) object.get("spigot");

        String fileDir = (String) spigotSettings.get("jar-location");
        String fileName = (String) spigotSettings.get("jar-name");

        SPIGOT_PATH = fileDir;
        SPIGOT_NAME = fileName;

        System.out.println("----- JARVIS -----");
        System.out.println("Registering jarvis settings...");
        System.out.println("Instance Settings ->");
        System.out.println("   home-location: " + instanceDir);
        System.out.println("Spigot Settings ->");
        System.out.println("   jar-directory: " + fileDir);
        System.out.println("   jar-name: " + fileName);
        System.out.println("------------------");
    }

    private JSONObject parseFile(String filePath) {
        try {
            return (JSONObject) new JSONParser().parse(new FileReader(Jarvis.class.getClassLoader().getResource(filePath).getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void createInstance(String type) {
        InstanceInfo info = getInstanceTypes().get(type);

        int startingPort = info.getStartingPort();
        int maxInstances = info.getMaxInstances();
        int currentInstances = getInstanceCount().get(type);

        int remaining = maxInstances - currentInstances;
        int id = (maxInstances - remaining) + 1;
        int nextPort = startingPort + id - 1;

        System.out.println("Starting instance " + info.getName().toUpperCase() + "-" + id + " @ localhost:" + nextPort);

        File plugins = new File(info.getPlugins());
        File maps = new File(info.getMaps());
        String spigot = SPIGOT_PATH + "/" + SPIGOT_NAME;

        File instanceDir = new File(INSTANCES_HOME + "/" + info.getName().toUpperCase() + "-" + id);
        if(!instanceDir.exists()) {
            instanceDir.mkdirs();
        }

        try {
            FileUtils.copyDirectory(plugins, instanceDir);
            FileUtils.copyDirectory(maps, instanceDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ProcessBuilder processBuilder = new ProcessBuilder(
                "java", "-server", "-Dcom.mojang.eula.agree=true", "-jar",
                "-Xmx" + info.getMemory() + "m",
                "-Xms" + info.getMemory() + "m",
                spigot,
                "--port", "" + nextPort,
                "--bukkit-settings", SPIGOT_PATH + "/bukkit.yml",
                "--spigot-settings", SPIGOT_PATH + "/spigot.yml"
        );

        processBuilder.directory(instanceDir);

        try {
            Process process = processBuilder.start();
            Instance instance = new SpigotInstance(info.getName(), id, process);
            getInstances().get(info.getName()).put(id, instance);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Total output should look something like 'instance setup LOBBY 1 2000'
        getInstance().getRedis().getPub().send("bungee", "instance setup " + info.getName() + " " + id + " " + nextPort);
    }

}
