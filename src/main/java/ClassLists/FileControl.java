
package ClassLists;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
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
    
    
}

