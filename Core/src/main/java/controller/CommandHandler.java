package controller;

import controller.instance.Instance;
import controller.instance.spigot.SpigotInstance;

import java.util.Scanner;

public class CommandHandler {

    private Jarvis instance;

    public CommandHandler(Jarvis instance) {
        this.instance = instance;
    }

    public void startCLI() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String start = scanner.nextLine();
            String[] msg = start.split(" ");
            String command = msg[0];

            if (command.equalsIgnoreCase("instance")) {
                if (msg.length == 3) {
                    if (msg[1].equalsIgnoreCase("create")) {

                        String type = msg[2];
                        if (!instance.getInstanceManager().isType(type)) {
                            Jarvis.error(type + " is not a valid type");
                            continue;
                        }

                        instance.getInstanceManager().createInstance(type);

                    } else if (msg[1].equalsIgnoreCase("delete")) {
                        String server = msg[2];
                        String[] instanceParams = server.split("-");

                        String type = instanceParams[0];
                        int id = Integer.parseInt(instanceParams[1]) - 1;
                        if(!instance.getInstanceManager().contains(type, server)) {
                            Jarvis.error("'" + server + "' does not exist");
                            continue;
                        }

                        Instance screenInstance = instance.getInstanceManager().getInstances().get(type).get(id);
                        instance.getInstanceManager().killScreen(screenInstance);
                        instance.getInstanceManager().getInstances().get(type).remove(id);

                    } else {
                        Jarvis.log("Invalid arguments. 'instance [create <type>, delete {id}]'");

                    }

                } else if (msg.length == 2) {
                    if (msg[1].equalsIgnoreCase("list")) {

                        instance.getInstanceManager().getInstances().forEach((type, instanceList) -> {
                            Jarvis.log("Listing [" + type + "] instances:");
                            for (Instance server : instanceList) {

                                if(server instanceof SpigotInstance) {
                                    SpigotInstance i = (SpigotInstance) server;
                                    Jarvis.log(i.toString());
                                }

                            }
                        });

                    } else {
                        Jarvis.error("Invalid arguments. 'instance [list]'");
                    }

                } else {
                    Jarvis.error("Invalid arguments. 'instance [list, type]'");
                }
            } else if (command.equalsIgnoreCase("help")) {
                Jarvis.error("Commands are: [instance, help]");
            } else {
                Jarvis.error("Invalid command. Type 'help'");
            }
        }
    }
}
