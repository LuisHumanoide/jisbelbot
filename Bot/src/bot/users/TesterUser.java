/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.users;

/**
 *
 * @author HumanoideFilms
 */
public class TesterUser {
    
    String name;
    String lastQuestion;

    public TesterUser(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastQuestion() {
        return lastQuestion;
    }

    public void setLastQuestion(String lastQuestion) {
        this.lastQuestion = lastQuestion;
    }
    
    public boolean hasQuestion(){
        if(lastQuestion!=null){
            if(lastQuestion.length()>1){
                return true;
            }
        }
        return false;
    }
    
}
