/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.users;

import bot.files.FileUtils;
import bot.lector;
import bot.logic.Msg;
import bot.logic.Rule;
import bot.logic.RulesList;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author HumanoideFilms
 */
public class Users {

    static HashSet<String> usersSala;
    public static ArrayList<User> users;
    public static ArrayList<TesterUser> tusers;
    static HashSet<String> saludados;

    /**
     * dar espacio en memoria a las listas de usuarios
     */
    public static void initUsers() {
        tusers = new ArrayList();
        users = new ArrayList();
        usersSala = new HashSet();
        saludados = new HashSet();
    }

    /**
     * añade a los usuaros que han sido saludados, 
     * perdonen el spanglish en el código
     * @param user 
     */
    public static void addSaludo(String user) {
        if (saludados.size() >= 150) {
            saludados.clear();
        }
        saludados.add(user);

    }

    /**
     * añade un usuario capaz de entrenar al bot
     * @param name 
     */
    public static void addTesterUser(String name) {
        if (findTUser(name) == -1) {
            tusers.add(new TesterUser(name));
            saveTesterUsers();
        }
    }

    /**
     * eliminar usuario que entrena al bot
     * @param name 
     */
    public static void removeTesterUser(String name) {
        int index = findTUser(name);
        if (index != -1) {
            tusers.remove(index);
            Msg.print("remover tester " + index);
            saveTesterUsers();
        }
    }

    /**
     * el usuario entrenador añade su pregunta, pero no se agrega a la lista 
     * hasta que le da respuestas, si no el programa truena
     * @param name
     * @param question 
     */
    public static void addTUserQuestion(String name, String question) {
        tusers.get(findTUser(name)).setLastQuestion(question);
    }

    /**
     * se agregan respuestas a la ultima pregunta del usuario que entrena
     * @param name
     * @param answer 
     */
    public static void addTUserAnswer(String name, String answer) {
        TesterUser tu = tusers.get(findTUser(name));
        if (tu.hasQuestion()) {
            Msg.print("agregado resp --------- " + answer);
            RulesList.createRule(tu.lastQuestion, answer);
        } else {
            Msg.print("agregado resp --------- " + "no tiene pregunta");
        }
    }

    /**
     * indica si ya se ha saludado a un usuario
     * para no saludarlo mas de 1 vez
     * @param user
     * @return 
     */
    public static boolean haSaludado(String user) {
        for (String user2 : saludados) {
            if (user.equals(user2)) {
                return true;
            }
        }
        return false;
    }

    /**
     * indica si un usuario ha sido añadido a la lista general de usuarios
     * @param name
     * @return 
     */
    public static boolean added(String name) {
        boolean addeduser = false;
        for (User u : users) {
            if (u.name.equals(name)) {
                addeduser = true;
            }
        }
        return addeduser;
    }

    /**
     * añade usuario a la lista general de usuarios
     * @param name 
     */
    public static void addUser(String name) {
        if (!added(name)) {
            users.add(new User(name));
            Msg.print("usuario agregado " + name);
        } else {
            Msg.print("ya existe usuario " + name);
        }
    }

    /**
     * encuentra un usuario
     * @param name
     * @return 
     */
    public static int findUser(String name) {
        int index = -1;
        for (User u : users) {
            if (u.name.equals(name)) {
                index = users.indexOf(u);
            }
        }
        return index;
    }

    /**
     * encuentra un usuario entrenador
     * @param name
     * @return 
     */
    public static int findTUser(String name) {
        int index = -1;

        for (TesterUser u : tusers) {
            if (u.getName().equals(name)) {
                index = tusers.indexOf(u);
            }
        }
        return index;
    }

    /**
     * almacena las reglas que un usuario ha activado
     * @param name
     * @param id 
     */
    public static void addRule(String name, int id) {
        users.get(findUser(name)).addRule(id);
    }

    /**
     * guarda la lista de usuarios entrenadores en un archivo
     */
    public static void saveTesterUsers() {
        String usercad = "";
        for (TesterUser t : tusers) {
            usercad = usercad + t.getName() + "\n";
        }
        FileUtils.write("tusers", usercad, "txt");
    }

    /**
     * abre desde un archivo la lista de usuarios entrenadores
     */
    public static void loadTesterUsers() {
        tusers = new ArrayList();
        ArrayList<String> tuserNames = lector.returnList("tusers.txt");
        for (String names : tuserNames) {
            if (names.length() > 1) {
                tusers.add(new TesterUser(names));
            }
        }
        Msg.print(" tam " + tusers.size());

    }

}
