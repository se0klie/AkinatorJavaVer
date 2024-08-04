/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.edu.espol.proyecto2ped;

import ClassLists.FileControl;
import static com.edu.espol.proyecto2ped.firstWindowController.maxOfQues;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class UserPageController implements Initializable {

    @FXML
    private TextField quesNum;
    @FXML
    private Button nextButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        firstWindowController.currentUser = FileControl.getUser(firstWindowController.currentUser);
        
    }    
    
    //ESTE EVENT ESTABA ANTES EN EL FXML DE FIRSTWINDOWCONTROLLER, LO CAMBIÉ ACÁ
    

    @FXML
    private void nextButton(MouseEvent event) throws IOException {
        String input = quesNum.getText();
            
        if (!input.isEmpty()) {
            if (Number.isNumeric(input)) {
                int num = Integer.parseInt(input);

                if (num > 0) {  // Verifico que el número sea positivo
                    maxOfQues = num;

                    // Cierra la ventana actual
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.close();

                    // Carga la nueva ventana principal
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/edu/espol/proyecto2ped/mainPage.fxml"));
                    Parent root = loader.load();
                    Scene scene = new Scene(root);

                    // Aplica el CSS a la nueva escena
                    scene.getStylesheets().add(getClass().getResource("/com/edu/espol/proyecto2ped/styles.css").toExternalForm());

                    Stage newStage = new Stage();
                    newStage.setTitle("Akinator");
                    newStage.setScene(scene);
                    newStage.show();
                } else {
                    firstWindowController.showAlert(Alert.AlertType.ERROR, "El número debe ser positivo.");
                }
            } else {
                firstWindowController.showAlert(Alert.AlertType.ERROR, "Ingrese un número entero válido.");
            }
        } else {
            firstWindowController.showAlert(Alert.AlertType.ERROR, "El campo no puede estar vacío.");
        }
    }

    @FXML
    private void showAchievements(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/edu/espol/proyecto2ped/achievements.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage newStage = new Stage();
        newStage.setTitle("Achievements");
        newStage.setScene(scene);
        newStage.show();
    }
    
    
}
