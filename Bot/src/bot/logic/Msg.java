/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.logic;

import bot.variables;

/**
 *
 * @author HumanoideFilms
 */
public class Msg {
    
    public static void print(String message){
        if(variables.log){
            System.out.println(message);
        }
    }
}
