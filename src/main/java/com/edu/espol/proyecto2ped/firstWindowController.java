package com.edu.espol.proyecto2ped;


import ClassLists.FileControl;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class firstWindowController implements Initializable{

    @FXML
    private Pane pane;
    @FXML
    private TextField quesNum;
    @FXML
    private Button nextButton;
    
    public static int numberOfQues = 0;
    public static int maxOfQues = 0;
    @FXML
    private Label nameGame;
    @FXML
    private Label description;
    @Override
    public void initialize(URL url, ResourceBundle rb){ //no me gusta q no se oueda ampliar la pantalla y qUE NO SE CENTREN LAS MOVIDAS AUXILIO
        try {
            String path = "/Users/hailiejimenez/Desktop/la odiada espol/estructuras/tareas/AkinatorJavaVer/src/main/resources/img/backgroundProy.png";
            Image image = new Image(new File(path).toURI().toString());

            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            pane.setBackground(new Background(backgroundImage));
            
            
        } catch (Exception e) {
            System.out.println("Error al cargar el logo: " + e.getMessage());
        }
        
        
    }
    
    //Se vuelve a leer el archivo y cargar las preguntas con este método
    
    public void initializeGame(){
        MainPageController.queueQuestions= FileControl.readLinesFromZip("Archive.zip","questionsDATA.txt");
    }
    
    
    
    @FXML
    public void nextButton(MouseEvent event) throws IOException {
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
                    showAlert(AlertType.ERROR, "El número debe ser positivo.");
                }
            } else {
                showAlert(AlertType.ERROR, "Ingrese un número entero válido.");
            }
        } else {
            showAlert(AlertType.ERROR, "El campo no puede estar vacío.");
        }
    }
    
    // Metodo para mostrar las alertas de mejor manera
    
    private void showAlert(AlertType type,String message){
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.show();
    }

}

