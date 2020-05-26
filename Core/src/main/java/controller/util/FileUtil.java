package controller.util;

import java.io.*;

public class FileUtil {

    /**
     * Replace a given field in any file with any string
     * @param file Source file
     * @param find String that you are replacing
     * @param replace What the string will be replaced with
     * @throws IOException
     */
    public static void replaceFile(File file, String find, String replace)
            throws IOException {

        String old = "";
        BufferedReader reader = new BufferedReader(new FileReader(file));

        String line = reader.readLine();
        while(line != null) {
            old = old + line + System.lineSeparator();
            line = reader.readLine();
        }

        String replaced = old.replace(find, replace);

        FileWriter writer = new FileWriter(file);
        writer.write(replaced);

        reader.close();
        writer.close();
    }

}
