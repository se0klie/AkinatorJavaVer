package com.edu.espol.proyecto2ped;


import ClassLists.Achievement;
import ClassLists.FileControl;
import ClassLists.Node;
import ClassLists.SearchTree;
import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;



public class MainPageController implements Initializable{

    @FXML
    private ImageView genieImage;

    @FXML
    private HBox hboxButtons;

    private Button noButtoon;

    @FXML
    private Label quesLabel;

    @FXML
    private VBox vboxDisplay;

    @FXML
    private VBox vboxQuestions;

    private Button yesButton;
    
    private Label numberQuesLabel;
    private boolean lost = false;
    private Printer printer = new Printer();
    private Node<String> currentNode;
    private SearchTree<String> questionTree;
    private int actualNumQues = 0;
    private int maxQuestions;
    private List<String> playerAnswers = new LinkedList<>(); //Sirve para guardar todas las respuestas del usuario
    private boolean hasGuessed;
    private GameFacade game = new GameFacade();
    public static Queue<String> queueQuestions = null;
    public static Map<String,List<String>> answers = null;
    
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (firstWindowController.currentUser != null) {
            System.out.println("USER: " + firstWindowController.currentUser.toString());
        } else {
            System.out.println("PLAYING AS GUEST");
        }
        initializeQuestionVBox();
        Image image = new Image(getClass().getResource("/genie.png").toExternalForm());
        queueQuestions = FileControl.readLinesFromZip("Archive.zip","questions.txt");
        genieImage.setImage(image);
        try {
            answers = FileControl.readAnswersFromZip("Archive.zip","answersDATA.txt");
            startGame();
        } catch (IOException ex) {
            System.out.println("Couldn't start the game.");
            ex.printStackTrace();
        }
    }
    
    private void setLabel(Label label) {
        label.setWrapText(true);
        label.setPadding(new Insets(5));
        label.setAlignment(Pos.CENTER);
        label.setMaxWidth(Double.MAX_VALUE);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setMinHeight(Region.USE_PREF_SIZE);
    }
    
    private void setHBox(HBox hbox) {
        hbox.setAlignment(Pos.CENTER);
        hbox.setPadding(new Insets(10));
        hbox.setSpacing(10);
    }
    
    public void startGame() throws IOException {
        // Obtener el "banco" de preguntas
        
        if (!queueQuestions.isEmpty()) {
            // Crear el árbol de preguntas
            questionTree = new SearchTree<>();
            questionTree = questionTree.buildQuestionTree(queueQuestions);
            questionTree.buildAnswersTree(answers);
            // Obtener la pregunta actual
            currentNode = questionTree.getRoot();
            // Configurar el número máximo de preguntas
            maxQuestions = firstWindowController.maxOfQues; 
            actualNumQues = 0;  // Reiniciar el número de preguntas
            playerAnswers.clear();  // Limpio las respuestas del jugador
            // Mostrar la primera pregunta
            showQuestion();
        }
        else{
            quesLabel.setText("No se pudo cargar el banco de preguntas");
        }
    }

    //METODO PARA MOSTRAR LAS PREGUNTAS
    private void showQuestion() {
        if(actualNumQues>=maxQuestions/2-1){
            Image img = new Image(getClass().getResource("/genieMad.png").toExternalForm());
            genieImage.setImage(img);
        }
        
        if(game.validateIfValidNode(currentNode)){
            if(currentNode!=null && actualNumQues<maxQuestions){ //VALIDACION DE QUE NO SEA NULL Y QUE EL NUMERO ACTUAL DE LA PREGUNTA NO SOBREPASE EL NUMERO INGREASDO DEL USUARIO
            
            quesLabel.setText(currentNode.getString());
            numberQuesLabel.setText("Pregunta #" + (actualNumQues + 1));
        }
            else{
                lost = true;
                results();
                numberQuesLabel.setText("");
            }
        } else {
            results();
        }
    }
   
    
    //Metodo para avanzar de pregunta 
    private void yesButtonQues(MouseEvent event) {
        playerAnswers.add("si");
        
        if(game.findAnimalFromUsersAnswers(answers, playerAnswers)!=null){
            results();
            
        }else if(currentNode.getYes()!=null){
            currentNode=currentNode.getYes().getRoot();
            actualNumQues++;
            showQuestion();
        }
        else{
            results();
        }
    }
    
    //Metodo para avanzar de pregunta 
    private void noButtonQues(MouseEvent event) {
        playerAnswers.add("no");
        if(game.findAnimalFromUsersAnswers(answers, playerAnswers)!=null){
            results();
            
        } else if(currentNode.getNo()!=null){
            
            currentNode=currentNode.getNo().getRoot();
            actualNumQues++;
            showQuestion();
        }
        else{
            results();
        }
    }
    //solo queria poner estas lineas en 1 mismo metodo, basicamente es para ver si el animal adivinado es correcto o no
    private void validateAch(){
        if(firstWindowController.currentUser!=null){
            LinkedList<Achievement> ach = Achievement.findWonAchievement(firstWindowController.currentUser, actualNumQues);
            if(!ach.isEmpty()){
                for (Achievement ac : ach) {
                    firstWindowController.currentUser.addAchievement(ac);
                }
            }
            FileControl.editUser(firstWindowController.currentUser);
        }
    }
    private void setResultsVBox(String animal){
        vboxDisplay.getChildren().clear();
        
        Label showAnimal = new Label("El animal en el que estás pensando es...\n"+animal+"... ¿estoy en lo correcto?");
        setLabel(showAnimal);
        showAnimal.getStyleClass().add("vboxQuestions");
        
        Button guessed = new Button();
        guessed.setText("¡Ese pensé!");
        guessed.setWrapText(true);
        guessed.getStyleClass().add("yesButton");
        guessed.addEventHandler(MouseEvent.MOUSE_CLICKED,event -> {
            System.out.println("clicked");
            if(firstWindowController.currentUser != null) {
                firstWindowController.currentUser.changeScore(true);
            }
            validateAch();
            winnerVBox(animal);
        });
        
        Button noGuessed = new Button();
        noGuessed.getStyleClass().add("noButtoon");
        noGuessed.setWrapText(true);
        noGuessed.setText("No es ese...");
        noGuessed.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (firstWindowController.currentUser != null) {
                firstWindowController.currentUser.changeScore(false);
            }
            validateAch();
            loserVBox();
        });
        
        hboxButtons.getChildren().clear();
        hboxButtons.getChildren().addAll(guessed,noGuessed);
        vboxDisplay.getChildren().addAll(showAnimal, hboxButtons);
    }
    
    private void winnerVBox(String animal) {
        vboxDisplay.getChildren().clear();
        Label l1 = new Label("Tu animal fue...\n"+animal);
        setLabel(l1);
        l1.getStyleClass().add("vboxQuestions");
        addButtons();
        vboxDisplay.getChildren().addAll(l1, hboxButtons);
    }
    
    private void loserVBox(){
        vboxDisplay.getChildren().clear();
        Label l1 = new Label("No pude adivinar el animal que pensaste...¿Qué hacemos ahora?");
        setLabel(l1);
        l1.getStyleClass().add("vboxQuestions");
        addButtons();
        vboxDisplay.getChildren().addAll(l1, hboxButtons);
    }
    
    private void initializeQuestionVBox(){
        vboxDisplay.getChildren().clear();
        vboxQuestions.getStyleClass().add("vboxQuestions");
        numberQuesLabel =  new Label();
        quesLabel = new Label();
        setLabel(quesLabel);
        setLabel(numberQuesLabel);
        
        yesButton = new Button("Sí");
        yesButton.getStyleClass().add("yesButton");
        yesButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
            yesButtonQues(event);
        });
        
        noButtoon = new Button("No");
        noButtoon.getStyleClass().add("noButtoon");
        noButtoon.addEventHandler(MouseEvent.MOUSE_CLICKED, event->{
            noButtonQues(event);
        });
        
        vboxQuestions.getChildren().addAll(numberQuesLabel,quesLabel);
        setHBox(hboxButtons);
        hboxButtons.getChildren().addAll(yesButton, noButtoon);
        vboxDisplay.getChildren().addAll(vboxQuestions, hboxButtons);
        
    }
    
    //Metodo para volver al fxml de firstPage y reiniciar el juego
    private void goToFirstPage(Event event) throws IOException {
        try {
            
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            App.setRoot("firstPage");
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo FXML: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    //Metodo para volver al fxml de userPage y reiniciar el juego
    private void goToUserPage(Event event) throws IOException {
        try {
            Stage currentStage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            App.setRoot("userPage");
        } catch (IOException ex) {
            System.out.println("Error al cargar el archivo FXML: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
    
    public void addButtons(){
        Button replay = new Button("Jugar de nuevo");
        replay.getStyleClass().add("noButtoon");
        
        //System.out.println("get global us: "+firstWindowController.getGlobalUser());
        replay.addEventHandler(MouseEvent.MOUSE_CLICKED,event ->{
            try {
                if (firstWindowController.getGlobalUser() != null) {
                    goToUserPage(event);
                } else {
                    goToFirstPage(event);
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        Button exit = new Button("Salir");
        exit.getStyleClass().add("noButtoon");
        
        exit.addEventHandler(MouseEvent.MOUSE_CLICKED,event->{
            Scene scene = genieImage.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.close();
            
        });
        
        hboxButtons.getChildren().clear();
        hboxButtons.getChildren().addAll(replay, exit);
    }

    private void results(){
        
        yesButton.setVisible(false);
        noButtoon.setVisible(false);
        vboxQuestions.getChildren().remove(numberQuesLabel);
        
        String animal = game.findAnimalFromUsersAnswers(answers, playerAnswers);
        List<String> possibleAnimals = game.findListAnimals(answers, playerAnswers);
        
        if(animal!=null){ //FOUND ANIMAL
            setResultsVBox(animal);
            
            
        } else if(lost){ //RAN OUT OF QUESTIONS
            quesLabel.setTextAlignment(TextAlignment.JUSTIFY);
            if(firstWindowController.currentUser != null) {
                firstWindowController.currentUser.changeScore(false);
            }
            quesLabel.setText("Puedes estar pensando en uno de estos: "+String.join(", ", possibleAnimals));
            validateAch();
            addButtons();
        } 
        else{ //ANIMAL DOESNT EXIST
            quesLabel.setTextAlignment(TextAlignment.JUSTIFY);
            if(firstWindowController.currentUser != null) {
                firstWindowController.currentUser.changeScore(false);
            }
            quesLabel.setText("El animal ingresado no existe. Puedes estar pensando en uno de estos: "+String.join(", ", possibleAnimals));
            validateAch();
            addButtons();
        }
        
    }
    

    
}
