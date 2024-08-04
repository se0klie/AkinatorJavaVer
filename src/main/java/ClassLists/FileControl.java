
package ClassLists;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
    
    
    public static boolean authentication(User user){
        User us = getUser(user);
        if(us !=null){
            return true;
        }
        return false;
    }
    
    public static void saveAchievementDB(User user) {
        if (getUser(user)!=null){
            try (BufferedWriter bw = new BufferedWriter(new FileWriter("userDB.txt", true))) { // 'true' to append
                bw.write(user.toString());
                bw.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public static void saveUserDB(User user){
        System.out.println("Saving to: " + new File("registro.txt").getAbsolutePath());

        //El true me permite hacer que no se sobreescriban los datos, si no que se almacenen
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("registro.txt",true))){
            if(user.getName()!=null && user.getPassword()!=null){
                writer.write(user.getName()+","+user.getPassword()+"\n");
            } 
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
    public static LinkedList<User> getUsersAchDB(){
        LinkedList<User> users = new LinkedList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("userDB.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                String name= parts[0];
                User us = new User(name);
                us.setPassword(parts[1]);
                
                String[] parts2 = parts[2].split("\\|");
                us.setWon(Integer.parseInt(parts2[0]));
                us.setLost(Integer.parseInt(parts2[1]));
                LinkedList<Achievement> achieved = new LinkedList<>();
                
                if(parts[3].contains(";")){ 
                    String[] ach = parts[3].split("; ");
                    for(String str : ach){
                        String[] achievement = str.split("-");
                        Achievement achNew = new Achievement(achievement[0],achievement[1]);
                        achieved.add(achNew);
                    }
                }
                
                us.setAchievements(achieved);
                users.add(us);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    public static User getUser(User us){
        LinkedList<User> users = getUsersAchDB();
        Iterator<User> usIt = users.iterator();
        while(usIt.hasNext()){
            User user = usIt.next();
            if(user.compareTo(us)==0){
                return user;
            }
        }
        return null;
    }
}

