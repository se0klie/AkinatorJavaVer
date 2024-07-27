/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.espol.proyecto2ped;

import ClassLists.SearchTree;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hailiejimenez
 */
public class GameFacade {
    
    public String findAnimalFromUsersAnswers(Map<String,List<String>> answerMap, List<String> userAnswers){
        Iterator<Map.Entry<String,List<String>>> iterator = answerMap.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,List<String>> mapResult = iterator.next();
            if(mapResult.getValue().equals(userAnswers)){
                return mapResult.getKey();
            }
        }
        return null;
    }
}
