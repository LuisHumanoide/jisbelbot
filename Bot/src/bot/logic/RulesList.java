/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.logic;

import bot.files.XMLFileUtils;
import java.util.ArrayList;

/**
 * Lista de reglas a la que el bot reacciona
 * @author HumanoideFilms
 */
public class RulesList {

    /**
     * lista de reglas
     */
    public static ArrayList<Rule> ruleList;

    /**
     * dar espacio en memoria a la lista de reglas
     */
    public static void initList() {
        ruleList = new ArrayList();
    }

    /**
     * añadir regla a la lista
     * @param r 
     */
    public static void addRule(Rule r) {
        ruleList.add(r);
    }

    /**
     * extrae el id mayor para asignarlo a una nueva regla
     * @return 
     */
    public static int getMaxId() {
        int max = 0;
        for (Rule r : ruleList) {
            if (r.id > max) {
                max = r.id;
            }
        }
        return max;
    }

    /**
     * extrae la posicion de reglas similares
     * @param r
     * @return 
     */
    public static int isSimilar(Rule r) {
        int nr = r.words.size();
        int index = -1;
        for (int i = 0; i < ruleList.size(); i++) {
            Rule list2 = ruleList.get(i);
            int nr2 = list2.words.size();
            if (nr == nr2) {
                ArrayList<StringList> words = r.words;
                int score = 1;
                for (int j = 0; j < words.size(); j++) {

                    StringList list = words.get(j);
                    for (String s : list.strings) {
                        if (list2.containsSingle(s)) {
                            score = score * 1;
                        } else {
                            score = score * 0;
                        }
                    }

                }

                if (score == 1) {
                    index = i;
                }
            }
        }
        return index;
    }

    public static void saveRulesFile() {

    }

    /**
     * crea una regla y la guarda
     * @param react
     * @param answers 
     */
    public static void createRule(String react, String... answers) {
        Rule r = new Rule();
        r.setid(RulesList.getMaxId() + 1);
        if (react.contains("er:")) {
            r.setER(react.replace("er:", ""));
            r.addReact("µ");
        } else {
            r.addReact(react);
            r.setER("µ");
        }
        int similar = (int) RulesList.isSimilar(r);
        if (similar != -1 && r.er.equals("µ")) {
            Msg.print("---------------regla similar a " + similar);
            if (answers != null) {
                for (String res : answers) {
                    RulesList.ruleList.get(similar).addResponse(res);
                }
            }

        } else {
            if (answers != null) {
                for (String res : answers) {
                    r.addResponse(res);
                }
            }
            RulesList.addRule(r);
        }

        XMLFileUtils.saveRules();
    }

}
