/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

/**
 *
 * @author HumanoideFilms
 */
public class Time {
    
    public static int deadTime=0;
    public static double calculateTime(String msg){
        return variables.time1+(0.3)*variables.timeC*msg.length();
    }
}
