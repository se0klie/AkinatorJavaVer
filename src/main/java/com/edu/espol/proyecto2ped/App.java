package com.edu.espol.proyecto2ped;

import ClassLists.FileControl;
import ClassLists.SearchTree;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        stage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("firstPage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // Aplica el CSS
        scene.getStylesheets().add(getClass().getResource("/com/edu/espol/proyecto2ped/styles.css").toExternalForm());

        stage.setScene(scene);
        stage.setTitle("Akinator");
        stage.show();
        
    }
    
    static void setRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource(fxml));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // Aplica el CSS
        scene.getStylesheets().add(App.class.getResource("/com/edu/espol/proyecto2ped/styles.css").toExternalForm());

        if (stage != null) {
            stage.setScene(scene);
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    
    public static void main(String[] args) throws IOException {
        Queue<String> ques = FileControl.readLinesFromZip("Archive.zip", "questionsDATA.txt");
        SearchTree<String> tree = new SearchTree<String>().buildQuestionTree(ques);
        Map<String, List<String>> answers = FileControl.readAnswersFromZip("Archive.zip", "answersDATA.txt");
        tree.buildAnswersTree(answers);
        tree.recorrerPreorden();
        launch();
    }
    
}
