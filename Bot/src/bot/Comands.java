/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bot;

import java.util.ArrayList;

/**
 *
 * @author HumanoideFilms
 */
public class Comands {
    
    String expression;
    ArrayList<String> responses;
    
    void generateCommands(String[] arr){
        expression=arr[0];
        responses=new ArrayList();
        for(int i=1;i<arr.length;i++){
            responses.add(arr[i]);
        }
        System.out.println("expression : "+expression);
        System.out.println("responses : "+responses.toString());

    }
    
}
