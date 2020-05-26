package controller.worker;

import controller.worker.util.JsonUtil;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;

public class JarvisWorker {

    public static void main(String[] args) {

        JSONObject config = JsonUtil.parseFile("config.json");
        JSONObject appHome = (JSONObject) config.get("app");
        String appDir = appHome.get("app-home").toString();

        String instanceName = args[0];
        ProcessBuilder builder = new ProcessBuilder(
                "screen", "-S", instanceName, "-X", "quit"
        );

        builder.directory(new File(appDir));

        try {
            builder.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
