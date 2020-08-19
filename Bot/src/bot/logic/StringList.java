/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.logic;

import java.util.ArrayList;

/**
 * Clase para operaciones especiales con strings
 * @author HumanoideFilms
 */
public class StringList {
    
    public ArrayList<String> strings;
    
    public StringList() {
        strings = new ArrayList();
    }
    
    public StringList(ArrayList<String> list) {
        this();
        for(String s:list){
            addString(s);
        }
    }
    
    public ArrayList<String> getStrings() {
        return strings;
    }
    
    public void setStrings(ArrayList<String> strings) {
        this.strings = strings;
    }
    
    public boolean addString(String string) {
        if (!strings.contains(string)) {
            strings.add(string);
            return true;
        }
        else{
            return false;
        }
    }
    
    public void deleteString(String string) {
        strings.remove(string);
    }
    
    public boolean contains(String string) {
        return strings.contains(string);
    }
    
    public boolean isEqual(String string) {
        return strings.get(0).equals(string);
    }
    
    public String getStringElement(){
        String value="";
        for(String s:strings){
            value=value+"/"+s;
        }
        value=value.substring(1, value.length());
        return value;
    }
    
}
