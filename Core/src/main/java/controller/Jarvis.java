package controller;

import controller.data.redis.Redis;
import controller.data.sql.SQLManager;
import controller.instance.InstanceManager;
import controller.util.JsonUtil;
import org.json.simple.JSONObject;

public class Jarvis {

    public static Jarvis instance;
    private static SQLManager sqlManager;
    private static InstanceManager instanceManager;
    private static Redis redis;
    private String[] sqlToken;

    public Jarvis() {
        loadSqlCredentials();
        log(sqlToken[0] + " " + sqlToken[1] + " " + sqlToken[2] + " " + sqlToken[3]);
        sqlManager = new SQLManager(sqlToken[0], 3306, sqlToken[1], sqlToken[2], sqlToken[3]);

        instanceManager = new InstanceManager(this);
        redis = new Redis("localhost", 6379);

        new Thread(() -> {
            try {
                redis.getSub().listen("jarvis");
            } catch(Exception e) {
                error("Could not connect to Redis server.");
            }

        }, "redisThread");
    }

    /**
     * Running instance of jarvis
     * @return instance
     */
    public static Jarvis getInstance() {
        return instance;
    }

    /**
     * SQL connector object
     * @return sqlManager
     */
    public SQLManager getSqlManager() {
        return sqlManager;
    }

    /**
     * Redis connector object
     * @return Redis
     */
    public Redis getRedis() {
        return redis;
    }

    /**
     * Instance Manager controls online instances
     * @return Instance Manager
     */
    public InstanceManager getInstanceManager() {
        return instanceManager;
    }

    /**
     * Load SQL server credentials into program's memory
     */
    private void loadSqlCredentials() {
        JSONObject object = JsonUtil.parseFile("config.json");
        JSONObject sql = (JSONObject) object.get("sql");

        sqlToken = new String[4];
        sqlToken[0] = sql.get("host").toString();
        sqlToken[1] = sql.get("db").toString();
        sqlToken[2] = sql.get("user").toString();
        sqlToken[3] = sql.get("pass").toString();
    }

    /**
     * Create main instance of Jarvis, load settings,
     * and start the CLI.
     * @param args
     */
    public static void main(String[] args) {
        instance = new Jarvis();
        instance.getInstanceManager().loadTypes();
        instance.getInstanceManager().loadSettings();

        CommandHandler commandHandler = new CommandHandler(instance);
        commandHandler.startCLI();
    }

    /**
     * Log error messages to the console
     * @param message Message being sent
     */
    public static void error(String message) {
        System.out.println("[Jarvis] [ERROR] " + message);
    }

    /**
     * Log message to the console
     * with just the regular prefix.
     * @param message Message being sent
     */
    public static void log(String message) {
        System.out.println("[Jarvis] " + message);
    }

}
