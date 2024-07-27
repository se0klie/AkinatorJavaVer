/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.espol.proyecto2ped;

import ClassLists.SearchTree;
import static com.edu.espol.proyecto2ped.MainPageController.answers;
import java.util.Iterator;
import java.util.LinkedList;
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
    
    public List<String> findListAnimals(Map<String,List<String>> answers,List<String> userAnswers){
        //Si no hay coincidencias exactas, hay distintos animales que podrían ser de acuerdo a la pregunta ( no se si me expliqué)
        List<String> possibleAnimals = new LinkedList<>();
        Iterator<Map.Entry<String,List<String>>> iterator = answers.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String,List<String>> mapResult_2 = iterator.next();
            if(mapResult_2.getValue().containsAll(userAnswers)){
                possibleAnimals.add(mapResult_2.getKey());
            }
        }
        return possibleAnimals;
    }
}
