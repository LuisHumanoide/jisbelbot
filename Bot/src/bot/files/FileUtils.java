/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;

/**
 *
 * @author HumanoideFilms
 */
public class FileUtils {

    /**
     * read the file and return a string
     *
     * @param path
     * @return the content of the file
     */
    public static String readFile(File file) {
        String content = "";
        try (InputStream in = Files.newInputStream(file.toPath());
                BufferedReader reader
                = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                //Msg.print(line);
                content = content + line + "\n";
            }
            in.close();
        } catch (IOException x) {
            System.err.println(x);
        }
        return content;
    }

    /**
     * Delete a file
     *
     * @param path
     */
    public static void deleteFile(String path) {
        File file = new File(path);
        file.delete();
    }

    /**
     * write a file
     *
     * @param name
     * @param cont
     */
    public static void write(String name, String cont, String ext) {
        FileWriter fichero = null;
        BufferedWriter pw = null;
        try {
            fichero = new FileWriter(name + "." + ext);
            pw = new BufferedWriter(fichero);
            pw.write(cont);
            pw.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    public static void createDir(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

}
