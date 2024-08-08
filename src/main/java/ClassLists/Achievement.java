/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassLists;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

/**
 *
 * @author hailiejimenez
 */
public class Achievement {
    private String name;
    private String description;
    private static LinkedList<Achievement> achievements = new LinkedList<>();
    public Achievement(){
        
    }
    
    public Achievement(String name, String desciption) {
        this.name = name;
        this.description = desciption;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public static LinkedList<Achievement> getAchievements() {
        return achievements;
    }
    
    public static void updateAchievements(){
        achievements = FileControl.readAchievements("Archive.zip", "achievements.txt");
    }
    
    public String toString(){
        return name + " - " + description;
    }
    
    public Achievement findWonAchievement(User us){
        int won = us.getWon();
        int lost = us.getLost();
        
        Achievement achieved = null;
        switch(won){
            case 1:
                achieved = achievements.get(0);
        }
        return null;
    }
}
