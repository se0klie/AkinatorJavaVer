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
    
    public static Achievement findWonAchievement(User us,int numQues){
        updateAchievements();
        
        int won = us.getWon();
        int lost = us.getLost();
        
        switch(won){
            case 1:
                return achievements.get(0);
            case 5:
                return achievements.get(1);
        }
        if(lost==1){
            return achievements.get(2);
            
        }
        
        if(numQues<=5){
            return achievements.get(3);
        }
        return null;
    }
}
