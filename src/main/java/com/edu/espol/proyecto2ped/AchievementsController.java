/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.edu.espol.proyecto2ped;

import ClassLists.Achievement;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        
        for(Achievement achievement: achievements){
            Image image = new Image(getClass().getResource("/star.png").toExternalForm());
            ImageView img = new ImageView(image);
            img.setFitHeight(60);
            img.setFitWidth(60);
            Label achievementLabel = new Label(achievement.getName()+" - "+achievement.getDescription());
            achievementLabel.getStyleClass().add("achievement-label");
            vbox.getChildren().addAll(img,achievementLabel);
        }
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    } 
    
}
