/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.users;

import bot.logic.Msg;
import java.util.HashSet;

/**
 *
 * @author HumanoideFilms
 */
public class User {
    
    String name;
    HashSet<Integer> rules;
    boolean talk;
    int gender;
    public double isTalkingWithMe=0;

    public User(String name, HashSet<Integer> rules, boolean talk) {
        this.name = name;
        this.rules = new HashSet();
        this.talk = talk;
    }
    
    public User(String name){
        this.name = name;
        this.rules = new HashSet();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void isTalkingWithMe(double probability){
        this.isTalkingWithMe=probability;
        Msg.print("prob de hablar con bot es ------------------ "+probability);
    }

    public boolean isTalk() {
        return talk;
    }

    public void setTalk(boolean talk) {
        this.talk = talk;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }
    
    public void addRule(int r){
        System.out.println("usuario "+name+ "tiene "+rules.size()+" reglas  se agrego la regla r"+r);
        rules.add(r);
    }
    
    
    
}
