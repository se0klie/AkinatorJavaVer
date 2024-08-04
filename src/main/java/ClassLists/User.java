/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ClassLists;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hailiejimenez
 */
public class User implements Comparable<User>{
    private String name;
    private String password;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
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
            str += ach.toString() + ";";
        }
        str += "]";
        return str;
    }

    public void setAchievements(LinkedList<Achievement> achievements) {
        this.achievements = achievements;
    }
    public String toString(){
        return name + ":" + won + "|" + lost +":" + achievementsStr();
    }

    @Override
    public int compareTo(User o) {
        if(this.name.compareTo(o.name)==0 && this.password.compareTo(o.password)==0){
            return 0;
        }
        return 1;
    }
    
    
    
}
