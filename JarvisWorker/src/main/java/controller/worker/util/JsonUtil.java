package controller.worker.util;

import controller.worker.JarvisWorker;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class JsonUtil {

    public static JSONObject parseFile(String filePath) {
        try {
            InputStream iStream = JarvisWorker.class.getResourceAsStream("/" + filePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(iStream, StandardCharsets.UTF_8));
            StringBuilder builder = new StringBuilder();

            String input = null;
            while((input = reader.readLine()) != null) {
                builder.append(input);
            }

            JSONParser parser = new JSONParser();

            return (JSONObject) parser.parse(builder.toString());
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

}
