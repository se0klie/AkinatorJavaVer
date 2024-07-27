
package ClassLists;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 *
 * @author hailiejimenez
 */
public class FileControl {
    public static Queue<String> readLinesFromFile(String file){
        Queue<String> queue = new LinkedList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                queue.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return queue;
    }
    
    
    
    public static Map<String,List<String>> readAnswersFromFile(String file) throws IOException{
        Map<String,List<String>> answerMap = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(file))){
            String line;
            while((line=br.readLine())!=null){
                String[] partes = line.split(" ");
                String animal = partes[0];
                List<String> answersList = new ArrayList<>();
                for (int i = 0; i < partes.length; i++) {
                    answersList.add(partes[i].toLowerCase());
                }
                answerMap.put(animal,answersList);
            }
        }
        return answerMap;
    }
    
    
}

