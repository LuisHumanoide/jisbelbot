/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.logic;

import bot.users.User;
import bot.users.Users;
import java.text.Normalizer;

/**
 *
 * @author HumanoideFilms
 */
public class AutomaticLearn {

    public static void learn(String message, String sender) {
        for (User user : Users.users) {
            if (user.getName().length() > 2) {
                if (message.contains(user.getName().toLowerCase())) {
                    Users.getUser(sender).talkingWith = user.getName();
                    Users.getUser(sender).lastSentence = message;
                }

               /* if (user.talkingWith.length() > 0) {
                    Msg.print(" SI <" + user.talkingWith + "> CONTIENE A " + sender.toLowerCase() + " Y MENSAJE CONTIENE " + user.getName().toLowerCase());
                }*/
                if (user.talkingWith.toLowerCase().contains(sender.toLowerCase()) && message.contains(user.getName().toLowerCase())) {
                    if (user.talkingWith.length() > 0 && user.lastSentence.length() > 0) {
                        RulesList.createRule(formatText(user.lastSentence.replace(sender.toLowerCase(), "name")), message.replace(user.getName().toLowerCase(), "sender"));
                        user.talkingWith = "";
                        user.lastSentence = "";
                        break;
                    }
                }
            }
        }
    }
    
    /**
     * metodo para limpiar el texto
     *
     * @param text
     * @return
     */
    public static String formatText(String text) {
        String message;
        message = Normalizer.normalize(text, Normalizer.Form.NFD);
        message = message.replaceAll("[^\\p{ASCII}]", "");
        message = message.toLowerCase();
        message = message.replaceAll("[-+.^:,?¿!¡]", " ");
        message = message.trim();
        return message;
    }

}
