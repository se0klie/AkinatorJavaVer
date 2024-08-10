/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassLists;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;

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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Achievement other = (Achievement) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.description, other.description);
    }
    
    public static LinkedList<Achievement> findWonAchievement(User us,int numQues){
        updateAchievements();
        LinkedList<Achievement> achievementsWon = new LinkedList<>(); // lista para poder retornar más de un logro
        
        int won = us.getWon();
        int lost = us.getLost();
        
        switch(won){
            case 1:
                achievementsWon.add(achievements.get(0));
                break;
            case 5:
                achievementsWon.add(achievements.get(1));
                break;
            case 10:
                achievementsWon.add(achievements.get(4));
                break;
        }
        if(lost==1){
            achievementsWon.add(achievements.get(2));
        }
        if(numQues<=5){
            achievementsWon.add(achievements.get(3));
        }
        return achievementsWon;
    }
}
