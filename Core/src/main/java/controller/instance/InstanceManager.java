package controller.instance;

import controller.Jarvis;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InstanceManager {

    private Jarvis instance;

    private Map<String, Integer> instanceCount;
    private Map<String, InstanceInfo> instanceTypes;
    private Map<String, Map<Integer, Instance>> instanceMap;

    public InstanceManager(Jarvis instance) {
        this.instance = instance;
        this.instanceCount = new ConcurrentHashMap<>();
        this.instanceTypes = new ConcurrentHashMap<String, InstanceInfo>();
        this.instanceMap = new ConcurrentHashMap<String, Map<Integer, Instance>>();
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
        try {
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(new FileReader(Jarvis.class.getClassLoader().getResource("instances/config.json").getFile()));
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void createInstance(String type) {
        InstanceInfo info = getInstanceTypes().get(type);

        ProcessBuilder processBuilder = new ProcessBuilder();
    }

}
