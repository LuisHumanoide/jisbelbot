/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import bot.users.Users;
import bot.files.XMLFileUtils;
import bot.logic.RulesList;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jibble.pircbot.IrcException;

/**
 *
 * @author HumanoideFilms
 */
public class Bot {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       
               // Now start our bot up.
        MessageAnswerer bot = new MessageAnswerer();
        RulesList.initList();
        XMLFileUtils.readRules();
        Users.loadTesterUsers();
        // Enable debugging output.
        bot.setVerbose(true);
        loadCommands();
        
        try {

         bot.connect("irc.chatzona.org");
        } catch (IOException ex) {
            Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IrcException ex) {
            Logger.getLogger(Bot.class.getName()).log(Level.SEVERE, null, ex);
        }

        bot.joinChannel(variables.channel);
    }
    
    public static void loadCommands(){
        for(int i=0;i<Listas.commands.size();i++){
            Comands c=new Comands();
            String commandstr[]=Listas.commands.get(i).split("\n");
            c.generateCommands(commandstr);
            Listas.commandList.add(c);
        }
    }
    
}
