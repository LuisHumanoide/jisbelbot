/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import bot.files.FileUtils;
import java.io.File;
import java.util.ArrayList;

/**
 *
 * @author HumanoideFilms
 */
public class Comands {
    
    String expression;
    ArrayList<String> responses;
    public static ArrayList<String> commands;
    
    /**
     * Genera los comandos para a partir de un mensaje aprender preguntas y respuestas
     * @param arr 
     */
    void generateCommands(String[] arr){
        expression=arr[0];
        responses=new ArrayList();
        for(int i=1;i<arr.length;i++){
            responses.add(arr[i]);
        }
        System.out.println("expression : "+expression);
        System.out.println("responses : "+responses.toString());
    }
    
    /**
     * Lee los comandos de un archivo de comandos
     */
    public static void readComands(){
        commands=new ArrayList<String>();
        String cad=FileUtils.readFile(new File("commands.txt"));
        String lines[]=cad.split("\n");
        for(String line:lines){
            commands.add(line);
        }
    }
    
    
    
}
