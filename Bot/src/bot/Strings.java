/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import bot.Markov.MarkovGraph;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author HumanoideFilms
 */
public class Strings {

    public static boolean matcher(String expresion, String string) {
        Pattern pat = Pattern.compile(expresion);
        Matcher mat = pat.matcher(string);
        return mat.matches();
    }

    /*
    public static boolean arrayMatcher(String expressions, String string){
        String expArray[]=expressions.split("")
    }*/
    public static boolean containsOR(String expressions, String string) {
        String arr[] = expressions.split(",");
        for (String expression : arr) {
            if (string.contains(string)) {
                return true;
            }
        }
        return false;
    }

    public static boolean containsAND(String expressions, String string) {
        String arr[] = expressions.split(",");
        for (String expression : arr) {
            if (!string.contains(string)) {
                return false;
            }
        }
        return true;
    }

    public static String chooseMessage(ArrayList<String> list) {
        String response;
        if (variables.markov) {
            response = randomMessage(list);
        } else {
            response = markovMessage(list);
        }
        return response;
    }

    public static String randomMessage(ArrayList<String> list) {
        int random = (int) (Math.random() * list.size());
        return list.get(random);
    }

    public static String markovMessage(ArrayList<String> list) {
        MarkovGraph graph = new MarkovGraph();
        for (String input : list) {
            graph.train(input);
        }
        return graph.getPhrase();
    }
}
