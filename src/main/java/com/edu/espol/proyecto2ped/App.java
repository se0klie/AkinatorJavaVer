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

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("firstPage"), 640, 480);
        stage.setScene(scene);
        stage.show();
        
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) throws IOException {
        Queue<String> ques = FileControl.readLinesFromZip("Archive.zip","questionsDATA.txt");
        SearchTree<String> tree = new SearchTree<String>().buildQuestionTree(ques);
        Map<String,List<String>> answers = FileControl.readAnswersFromZip("Archive.zip","answersDATA.txt");
        tree.buildAnswersTree(answers);
        tree.recorrerPreorden();
        launch();
    }

}