/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import java.util.ArrayList;

/**
 *
 * @author HumanoideFilms
 */
public class Listas {
    static ArrayList<String> saludos=lector.returnList("saludos.txt");
    static ArrayList<String> salaSola=lector.returnList("salaSola.txt");
    static ArrayList<String> commands=lector.comandList("comandos.txt");
    static ArrayList<Comands> commandList=new ArrayList();
    
    static void reloadList(){
        saludos.clear();
        commands.clear();
        commandList.clear();
        salaSola.clear();
        saludos=lector.returnList("saludos.txt");
        salaSola=lector.returnList("salaSola.txt");
        commands=lector.comandList("comandos.txt");
        Bot.loadCommands();
    }
}
