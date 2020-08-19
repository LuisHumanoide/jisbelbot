/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.logic;

import bot.Strings;
import bot.variables;
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
        for (int i = 0; i < words.size(); i++) {
            StringList list = words.get(i);
            for (String s : word) {
                if (list.contains(s)) {
                    score++;
                }
            }
        }
        return new double[]{score / words.size(), score};
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
        double value = Math.random() * responses.size();
        return responses.get((int) value);
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
