/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.edu.espol.proyecto2ped;

import ClassLists.Achievement;
import ClassLists.User;
import static com.edu.espol.proyecto2ped.firstWindowController.maxOfQues;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class UserPageController implements Initializable {

    @FXML
    private HBox starHBox;
    @FXML
    private TextField quesNum;
    @FXML
    private Button nextButton;
    @FXML
    private Label usernameLabel;
    private static User user=null;
    private LinkedList<Achievement> achievements;
    private boolean achievementCheck;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        usernameLabel.setText(firstWindowController.currentUser.getName());
        usernameLabel.setAlignment(Pos.CENTER);
        achievements = firstWindowController.currentUser.getAchievements();
        achievementCheck = !achievements.isEmpty();
        if (achievementCheck) {
            showStars();
        }
    }
    
    public static void setUser(User us){
        user = us;
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
        
        // Para que no se muestre la pantalla de logros si el usuario no tiene.
        if(!achievementCheck){
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setContentText("No cuentas con logros. Comienza a jugar para avanzar.");
            a.show();
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/edu/espol/proyecto2ped/achievements.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setTitle("Achievements");
            newStage.setScene(scene);
            newStage.show();
        }
    }
    
    private void showStars() {
        starHBox.getChildren().clear();
        for(Achievement achievement: achievements){
            Image image = new Image(getClass().getResource("/star.png").toExternalForm());
            ImageView img = new ImageView(image);
            img.setFitHeight(60);
            img.setFitWidth(60);
            starHBox.getChildren().add(img);
        }
    }
    
    @FXML
    private void logOut(ActionEvent event) throws IOException {
        App.setRoot("firstPage");
        firstWindowController.currentUser = null;
    }
}
