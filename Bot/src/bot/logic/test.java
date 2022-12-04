/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot.logic;

import bot.files.XMLFileUtils;
import java.text.Normalizer;

/**
 *
 * @author HumanoideFilms
 */
public class test {
    
    public static void main (String args[]){
        /*
        Rule r1=new Rule();
        RulesList.initList();
        r1.addStringList("hol");
        r1.addStringList("como","komo");
        r1.addStringList("estas");
        
        Rule r=new Rule();
        RulesList.initList();
        r.addReact("hola todos como/komo/joj estas");
        r.addResponse("bien");
        r.addResponse("muy bien");
        r.addResponse("excelente");
        r.addResponse("excelentisimo");
        if(r.willRespond("hola komo estas tu")){
            System.out.println(r.getRandomResponse());
        }
        
        Rule r2=new Rule();
        r2.addReact("hola como/komo estais");
        
        RulesList.addRule(r1);
        RulesList.addRule(r);
        RulesList.addRule(r2);
        System.out.println(RulesList.isSimilar(r2));
*/
        
        //XMLFileUtils.saveRules();
        /*XMLFileUtils.readRules();
        Rule r2=new Rule();
        r2.addReact("regla1 asd/sdfg/dfg asdf/rety");
        r2.addResponse("asdfsadf");
        RulesList.addRule(r2);
        XMLFileUtils.saveRules();*/
        //Msg.print(""+(double) Math.exp((double)(-Math.pow((double)(3-30)/(double)10, 2))));
        String name="^nick^";
        String msg="hola soy ^nick^    ^nick^";
        Msg.print(formatText(msg));
        
        
    }
    
     public static String formatText(String text) {
        String message;
        message = Normalizer.normalize(text, Normalizer.Form.NFD);
        message = message.replaceAll("[^\\p{ASCII}]", "");
        message = message.toLowerCase();
        message = message.replaceAll("[-+.^:,?¿!¡]", " ");       
        message = message.trim();
        message = message.replaceAll("\\s+", " ");
        return message;
    }

    
}
