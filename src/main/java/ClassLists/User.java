/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassLists;

import java.util.LinkedList;

/**
 *
 * @author hailiejimenez
 */
public class User {
    private String name;
    private int won;
    private int lost;
    private LinkedList<Achievement> achievements;

    public User(String name) {
        this.name = name;
        this.achievements = new LinkedList<>();
        won = 0;
        lost = 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWon(int won) {
        this.won = won;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public String getName() {
        return name;
    }

    public int getWon() {
        return won;
    }

    public int getLost() {
        return lost;
    }

    public LinkedList<Achievement> getAchievements() {
        return achievements;
    }
    
    public void addAchievement(Achievement ach){
        if(!achievements.contains(ach)){
            achievements.add(ach);
        }
    }
    
    public String achievementsStr(){
        String str = "[";
        for(Achievement ach : achievements){
            str += ach.getName() + "; ";
        }
        str += "]";
        return str;
    }
    public String toString(){
        return name + ":" + won + "|" + lost +":" + achievementsStr();
    }
    
}
