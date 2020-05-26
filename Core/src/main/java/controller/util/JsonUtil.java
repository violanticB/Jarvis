package controller.util;

import controller.Jarvis;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonUtil {

    /**
     * Load a JSONObject from a file in the resources folder
     * @param filePath Path to file
     * @return json object
     */
    public static JSONObject parseFile(String filePath) {
        try {
            InputStream iStream = Jarvis.class.getResourceAsStream("/" + filePath);
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
