/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HumanoideFilms
 */
public class lector {

    public static ArrayList returnList(String nombre) {
        String array[] = null;
        ArrayList<String> list = new ArrayList();
        try {
            array = muestraContenido(nombre).split("\n");
        } catch (IOException ex) {
            Logger.getLogger(lector.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (String text : array) {
            list.add(text);
        }
        return list;
    }

    public static ArrayList comandList(String nombre) {
        String array[] = null;
        ArrayList<String> list = new ArrayList();
        try {
            array = muestraContenido(nombre).split("-----\n");
        } catch (IOException ex) {
            Logger.getLogger(lector.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (String text : array) {
            list.add(text.replaceAll("name", variables.name.toLowerCase()));
        }
        return list;
    }

    public static String muestraContenido(String archivo) throws FileNotFoundException, IOException {
        String cadena;
        FileReader f = new FileReader(archivo);
        BufferedReader b = new BufferedReader(f);
        String ac = "";
        while ((cadena = b.readLine()) != null) {
            ac = ac + cadena + "\n";
        }
        b.close();
        return ac;
    }
}
