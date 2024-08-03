
package ClassLists;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 *
 * @author hailiejimenez
 */
public class FileControl {
    public static Queue<String> readLinesFromZip(String zipFilePath, String fileNameInZip) {
        //zipFilePath => nombre del ZIP             fileNameInZip =>  nombre del file en el zip
        Queue<String> queue = new LinkedList<>();
        
        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            ZipEntry entry = zipFile.getEntry(fileNameInZip);
            if (entry != null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        queue.add(line);
                    }
                }
            } else {
                System.out.println("File " + fileNameInZip + " not found in the ZIP archive.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return queue;
    }
    
    public static Map<String, List<String>> readAnswersFromZip(String zipFilePath, String fileNameInZip) throws IOException {
        Map<String, List<String>> answerMap = new HashMap<>();

        try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            ZipEntry entry = zipFile.getEntry(fileNameInZip);
            if (entry != null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] partes = line.split(" ");
                        String animal = partes[0];
                        List<String> answersList = new ArrayList<>();
                        for (int i = 1; i < partes.length; i++) {
                            answersList.add(partes[i].toLowerCase());
                        }
                        answerMap.put(animal, answersList);
                    }
                }
            } else {
                System.out.println("File " + fileNameInZip + " not found in the ZIP archive.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return answerMap;
    }
    
    public static LinkedList<Achievement> readAchievements(String zipFilePath, String fileNameInZip){
        LinkedList<Achievement> achievements = new LinkedList<>();
         try (ZipFile zipFile = new ZipFile(zipFilePath)) {
            ZipEntry entry = zipFile.getEntry(fileNameInZip);
            if (entry != null) {
                try (BufferedReader br = new BufferedReader(new InputStreamReader(zipFile.getInputStream(entry)))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] partes = line.split(" - ");
                        Achievement newAchieve = new Achievement(partes[0],partes[1]);
                        achievements.add(newAchieve);
                    }
                }
            } else {
                System.out.println("File " + fileNameInZip + " not found in the ZIP archive.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
         return achievements;
    }
    
    public static LinkedList<User> getUsers() {
        LinkedList<User> users = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("userDB.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":");
                String name = parts[0];
                User us = new User(name);
                
                System.out.println(parts[1]);
                String[] score = parts[1].split("\\|");
                
                us.setWon(Integer.parseInt(score[0]));
                us.setLost(Integer.parseInt(score[1]));
                
                parts[2].replace("\\[", " ");
                parts[2].replace("\\]", " ");
                
                if(!parts[2].isEmpty()){
                    String[] parts2 = parts[1].split("; ");
                    for(String ach : parts2){
                        Achievement achievement = new Achievement();
                        try{
                            achievement = achievement.getAchievementByName(name);
                            us.addAchievement(achievement);
                        } catch (Exception e){
                            
                        }
                    }
                }
                

                users.add(us);
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public static boolean userExists(String name){
        Iterator<User> it = getUsers().iterator();
        boolean found = false;
        while(it.hasNext()){
            User us = it.next();
            if(us.getName().compareTo(name)==0){
                found = true;
            }
        }
        return found;
    }
    public static void writeUserToFile(User user) {
        if (!userExists(user.getName())){
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("userDB.txt", true))) { // 'true' to append
                bw.write(user.toString());
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
}

