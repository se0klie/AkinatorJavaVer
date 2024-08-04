/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.edu.espol.proyecto2ped;

import ClassLists.Achievement;
import ClassLists.FileControl;
import static ClassLists.FileControl.readAchievements;
import ClassLists.User;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class AchievementsController implements Initializable {

    @FXML
    private ScrollPane paneAchievements;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        VBox achievementsVbox= createVBoxAchievements();
        paneAchievements.setContent(achievementsVbox);
        URL cssURL = getClass().getResource("/com/edu/espol/proyecto2ped/styles.css");
        if (cssURL != null) {
            paneAchievements.getStylesheets().add(cssURL.toExternalForm());
        } else {
            System.err.println("No se encontró el archivo CSS en la ruta especificada.");
        }

    }    
    
    public VBox createVBoxAchievements(){
        VBox vbox = new VBox();
        
        vbox.setSpacing(10);
        
        vbox.getStyleClass().add("achievement-vbox");
        LinkedList<Achievement> achievements = firstWindowController.currentUser.getAchievements();
        if(achievements.isEmpty()){
            Alert a = new Alert(AlertType.INFORMATION);
            a.setContentText("No cuentas con logros. Comienza a jugar para avanzar.");
            a.show();
        }
        
        for(Achievement achievement: achievements){
            Label achievementLabel = new Label(achievement.getName()+" - "+achievement.getDescription());
            achievementLabel.getStyleClass().add("achievement-label");
            vbox.getChildren().add(achievementLabel);
            
        }
        return vbox;
    } 
    
}
