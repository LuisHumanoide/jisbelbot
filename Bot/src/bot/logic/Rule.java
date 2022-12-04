/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.logic;

import bot.Markov.MarkovGraph;
import bot.Strings;
import bot.files.FileUtils;
import bot.variables;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author HumanoideFilms
 */
public class Rule {

    public ArrayList<StringList> getWords() {
        return words;
    }

    public void setWords(ArrayList<StringList> words) {
        this.words = words;
    }

    ArrayList<StringList> words;
    public String er;
    public int id = -1;
    public String expRules;
    public ArrayList<String> responses;

    public Rule() {
        expRules = "1";
        words = new ArrayList();
        responses = new ArrayList();
    }

    public void setid(int id) {
        this.id = id;
    }

    public void setExpRules(String exp) {
        expRules = exp;
    }

    public void addStringList(String... strings) {
        ArrayList<String> list;
        list = new ArrayList();
        for (String s : strings) {
            list.add(s);
        }
        words.add(new StringList(list));
    }

    public double[] contains(String[] word) {
        double score = 0;
        double score2= 0;
        String array[]=FileUtils.readFile(new File("scores.txt")).split("\n");
        for (int i = 0; i < words.size(); i++) {
            StringList list = words.get(i);
            for (String s : word) {
                if (list.contains(s)) {
                    score=score+1;
                    score2=score2+s.length()+getScore(s,array);;
                }
            }
        }
        return new double[]{(double)score / (double)words.size(), score2, 
            (double) Math.exp((double)(-Math.pow((double)(words.size()-word.length)/(double)5, 2)))};
    }
    
    public int wordsLenght(StringList list){
        int ac=0;
        for(String st:list.getStrings()){
            ac=ac+st.length();
        }
        return ac;
    }
    
    public int getScore(String word, String array[]){
        int score=0;
        for(String cad:array){
            String values[]=cad.split(" ");
            if(values[0].equals(word)){
                score=Integer.parseInt(values[1]);
                return score;
            }
        }
        return score;
    }

    public void setER(String er) {
        this.er = er;
    }

    public boolean matchER(String cad) {
        return Strings.matcher(er, cad);
    }

    public boolean containsSingle(String word) {
        for (int i = 0; i < words.size(); i++) {
            StringList list = words.get(i);
            if (list.contains(word)) {
                return true;
            }
        }
        return false;
    }

    public void addResponse(String response) {
        responses.add(response);
    }

    public String getRandomResponse() {
        Msg.print("- - - - - - - - random responde");
        double value = Math.random() * responses.size();
        return responses.get((int) value);
    }

    public String getMarkovResponse() {
        Msg.print("- - - - - - - - markov responde");
        MarkovGraph graph = new MarkovGraph();
        for (String input : responses) {
            graph.train(input);
        }
        return graph.getPhrase();
    }

    public String getResponse() {
        String response;
        if (variables.markov) {
            response = getMarkovResponse();
        } else {
            response = getRandomResponse();
        }
        return response;
    }

    public boolean willRespond(String sentence) {
        String[] sarray = sentence.toLowerCase().split(" ");
        double[] scores = contains(sarray);
        if (scores[0] > 0.8 || matchER(sentence)) {
            return true;
        } else {
            return false;
        }
    }

    public void addReact(String s) {
        String[] andArray = s.split(" ");
        for (String sa : andArray) {
            String orArray[] = sa.split("/");
            addStringList(orArray);
        }
    }

    public String printReact() {
        String ac = "";
        for (int i = 0; i < words.size(); i++) {
            ac = ac + " " + getStringElement(i);
        }
        ac = ac.substring(1, ac.length());
        return ac;

    }

    public String getStringElement(int i) {
        String value = "";
        for (String s : words.get(i).strings) {
            value = value + "/" + s;
        }
        value = value.substring(1, value.length());
        return value;
    }

}
