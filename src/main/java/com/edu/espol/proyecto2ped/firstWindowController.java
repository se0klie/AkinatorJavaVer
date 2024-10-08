package com.edu.espol.proyecto2ped;


import ClassLists.FileControl;
import ClassLists.User;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class firstWindowController implements Initializable{
    
    @FXML
    private StackPane stackPane;
    @FXML
    private Pane pane;
    @FXML
    private TextField quesNum;
    @FXML 
    private TextField name;
    
    public static User currentUser = null;
    public static int numberOfQues = 0;
    public static int maxOfQues = 0;
    @FXML
    private PasswordField passwordUser;
    @FXML
    private Button sesionInvited;
    @FXML
    private Button buttonLogin;
    
    public void initialize(URL url, ResourceBundle rb){ //no me gusta q no se oueda ampliar la pantalla y qUE NO SE CENTREN LAS MOVIDAS AUXILIO
        currentUser = null;
        /*
        try {
            String path = "/Users/hailiejimenez/Desktop/la odiada espol/estructuras/tareas/AkinatorJavaVer/src/main/resources/img/backgroundProy.png";
            Image image = new Image(new File(path).toURI().toString());

            BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
            BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
            pane.setBackground(new Background(backgroundImage));
            
            
        } catch (Exception e) {
            System.out.println("Error al cargar el logo: " + e.getMessage());
        }
        */
        
        createContent();
        initializeGame(); //Extraído del método goToFirstPage()
    }
    
    private void createContent() {
        ObjectProperty<Integer> backgroundOffsetProperty = new SimpleObjectProperty<>(0);

        ImageView background = new ImageView(this.getClass().getResource("/img/placeholder.jpg").toExternalForm());
        background.setViewport(new Rectangle2D(0, 0, 700, 568));

        backgroundOffsetProperty.addListener(observable -> {
            background.setViewport(new Rectangle2D(backgroundOffsetProperty.get(), 0, stackPane.widthProperty().doubleValue(), stackPane.heightProperty().doubleValue()));
        });
        
        background.fitWidthProperty().bind(stackPane.widthProperty());
        background.fitHeightProperty().bind(stackPane.heightProperty());
        createBackgroundAnimation(backgroundOffsetProperty).play();
        stackPane.getChildren().add(0, background); // se añade al inicio
    }

    private Animation createBackgroundAnimation(ObjectProperty<Integer> backgroundOffsetProperty) {
        Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(36000));
                setInterpolator(Interpolator.LINEAR);
            }

            protected void interpolate(double frac) {
                int backgroundOffset = (int) ((frac * 8896 * 0.5)) % 8896;
                backgroundOffsetProperty.set(backgroundOffset);
            }
        };
        animation.setCycleCount(Animation.INDEFINITE);
        return animation;
    }
    
    //Se vuelve a leer el archivo y cargar las preguntas con este método
    
    public void initializeGame(){
        MainPageController.queueQuestions= FileControl.readLinesFromZip("Archive.zip","questions.txt");
        System.out.println("Preguntas cargadas: " + MainPageController.queueQuestions.size());
    }
    

    // Metodo para mostrar las alertas de mejor manera
    
    public static void showAlert(AlertType type,String message){
        Alert alert = new Alert(type);
        alert.setContentText(message);
        alert.show();
    }
    
    //METODO PARA JUGAR COMO INVITADO

    @FXML
    private void playAsGuest(ActionEvent event) throws IOException {
        String input = quesNum.getText();
        if(input.isEmpty()){
            showAlert(AlertType.ERROR, "Debe ingresar un número de preguntas antes");
        }
        else{    
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
    }
    
    //IR AL FXML DE USERPAGE
    @FXML
    private void goToProfile(ActionEvent event) throws IOException {
        String name_user=name.getText();
        String password_user = passwordUser.getText();
        User us = new User(name_user);
        us.setPassword(password_user);
        if(name_user.isEmpty() || password_user.isEmpty()){
            showAlert(AlertType.ERROR, "Debe rellenar los campos de usuario");
        } else if(FileControl.authentication(us)){
            currentUser = FileControl.getUser(us);
            /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/edu/espol/proyecto2ped/userPage.fxml"));
            Parent userPage = loader.load();          
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(userPage);
            stage.setScene(scene);
            stage.show();*/
            System.out.println("user exists");
            App.setRoot("userPage");
        }
        else{
            try{
                
                currentUser = new User(name_user);
                currentUser.setPassword(password_user);
                FileControl.editUser(currentUser);
                UserPageController.setUser(currentUser);
                System.out.println("created us: " + currentUser.toString());
                
                /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/edu/espol/proyecto2ped/userPage.fxml"));
                Parent userPage = loader.load();          
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(userPage);
                stage.setScene(scene);
                stage.show();*/
                App.setRoot("userPage");
            }
            catch(IOException e){
                System.out.println("Error al cargar el archivo FXML: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    public static User getGlobalUser(){
        return currentUser;
    }

}

