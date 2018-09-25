package controller;

import controller.data.redis.Redis;
import controller.instance.InstanceManager;

import java.util.Scanner;

public class Jarvis {

    public static Jarvis instance;

    private static Redis redis;
    private static InstanceManager instanceManager;

    private Jarvis() {
        instanceManager = new InstanceManager(this);

        try {
            redis = new Redis("localhost", 0000);
        } catch(Exception e) {
            log("Could not connect to Redis server.");
        }
    }

    public InstanceManager getInstanceManager() {
        return instanceManager;
    }

    public static Jarvis getInstance() {
        return instance;
    }

    public Redis getRedis() {
        return redis;
    }

    public static void main(String[] args) {
        instance = new Jarvis();
        instance.getInstanceManager().loadTypes();
        instance.getInstanceManager().loadSettings();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String start = scanner.nextLine();
            String[] msg = start.split(" ");
            String command = msg[0];

            if (command.equalsIgnoreCase("instance")) {
                if (msg.length == 3) {
                    if (msg[1].equalsIgnoreCase("create")) {
                        String type = msg[2];
                        instance.getInstanceManager().createInstance(type);

                    } else if (msg[1].equalsIgnoreCase("delete")) {
                        log("TODO");

                    } else {
                        log("Invalid arguments. 'instance [create <type>, delete {id}]'");

                    }

                    continue;
                } else if (msg.length == 2) {
                    if (msg[1].equalsIgnoreCase("list")) {

                    } else if (msg[1].equalsIgnoreCase("type")) {

                    } else {
                        log("Invalid arguments. 'instance [list, type]'");
                    }

                    continue;
                } else {
                    log("Invalid arguments. 'instance [list, type]'");

                    continue;
                }
            } else if (command.equalsIgnoreCase("help")) {
                log("Commands are: [instance, help]");
            } else {
                log("Invalid command. Type 'help'");
            }
        }
    }

    public static void log(String message) {
        System.out.println("[JARVIS] " + message);
    }

}
