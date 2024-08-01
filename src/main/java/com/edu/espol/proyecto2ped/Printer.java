/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edu.espol.proyecto2ped;

import static com.edu.espol.proyecto2ped.MainPageController.answers;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hailiejimenez
 */
public class Printer {
    public void printMap(Map<String, List<String>> answers){
        for (Map.Entry<String, List<String>> entry : answers.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }
    }
}
