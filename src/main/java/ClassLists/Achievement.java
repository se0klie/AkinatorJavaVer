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
    private Map<String,Achievement> achievements;

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

    public Map<String,Achievement> getAchievements() {
        return achievements;
    }
    
    public void updateAchievements(){
        LinkedList<Achievement> achievementsList = FileControl.readAchievements("Archive.zip", "achievements.txt");
        Iterator<Achievement> itAch = achievementsList.iterator();
        while(itAch.hasNext()){
            Achievement ach = itAch.next();
            if(achievements.get(ach.name)==null){
                achievements.put(ach.name, ach);
            }
        }
    }
    
    public String toString(){
        return name + " - " + description;
    }

    public Achievement getAchievementByName(String name){
        return achievements.get(name);
    }
    
}
