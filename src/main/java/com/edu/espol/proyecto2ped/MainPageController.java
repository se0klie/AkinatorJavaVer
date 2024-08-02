package com.edu.espol.proyecto2ped;


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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;



public class MainPageController implements Initializable{

    @FXML
    private ImageView genieImage;

    @FXML
    private HBox hboxButtons;

    @FXML
    private Button noButtoon;

    @FXML
    private Label quesLabel;

    @FXML
    private VBox vboxDisplay;

    @FXML
    private VBox vboxQuestions;

    @FXML
    private Button yesButton;
    
    @FXML
    private Label numberQuesLabel;
    
    private Printer printer = new Printer();
    private Node<String> currentNode;
    private SearchTree<String> questionTree;
    private int actualNumQues = 0;
    private int maxQuestions;
    private List<String> playerAnswers = new LinkedList<>(); //Sirve para guardar todas las respuestas del usuario

    private GameFacade game = new GameFacade();
    public static Queue<String> queueQuestions = FileControl.readLinesFromZip("Archive.zip","questionsDATA.txt");
    public static Map<String,List<String>> answers = null;
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeQuestionVBox();
        Image image = new Image(getClass().getResource("/genie.png").toExternalForm());
        
        genieImage.setImage(image);
        
        try {
            answers = FileControl.readAnswersFromZip("Archive.zip","answersDATA.txt");
            startGame();
        } catch (IOException ex) {
            System.out.println("Couldn't start the game.");
            ex.printStackTrace();
        }
 
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
        if(currentNode!=null && actualNumQues<maxQuestions){ //VALIDACION DE QUE NO SEA NULL Y QUE EL NUMERO ACTUAL DE LA PREGUNTA NO SOBREPASE EL NUMERO INGREASDO DEL USUARIO
            
            quesLabel.setText(currentNode.getString());
            numberQuesLabel.setText("Pregunta #" + (actualNumQues + 1));
        }
        else{
            results();
            numberQuesLabel.setText("");
            yesButton.setDisable(true); // desactivo los botones para que no pueda avanzar
            noButtoon.setDisable(true);
        }
    }
   
    
    //Metodo para avanzar de pregunta 
    @FXML
    private void yesButtonQues(MouseEvent event) {
        playerAnswers.add("si");
        
        if(game.findAnimalFromUsersAnswers(answers, playerAnswers)!=null){
            results();
            
        }
        if(currentNode.getYes()!=null){
            currentNode=currentNode.getYes().getRoot();
            actualNumQues++;
            showQuestion();
        }
        else{
            results();
        }
    }
    
    //Metodo para avanzar de pregunta 
    @FXML
    private void noButtonQues(MouseEvent event) {
        playerAnswers.add("no");
        if(game.findAnimalFromUsersAnswers(answers, playerAnswers)!=null){
            results();
            
        }
        if(currentNode.getNo()!=null){
            
            currentNode=currentNode.getNo().getRoot();
            actualNumQues++;
            showQuestion();
        }
        else{
            results();
        }
    }
    
    private void setResultsVBox(String animal){
        vboxDisplay.getChildren().clear();
        Label showAnimal = new Label("El animal en el que estás pensando es...");
        showAnimal.getStyleClass().add("vboxQuestions");
        Label animal2 = new Label(animal);
        animal2.getStyleClass().add("vboxQuestions");
        HBox buttons = new HBox();
        Button replay = new Button("Jugar de nuevo");
        Button exit = new Button("Salir");
        buttons.setAlignment(Pos.CENTER);
        buttons.setPadding(new Insets(10));
        buttons.setSpacing(10);
        buttons.getChildren().addAll(replay, exit);
        buttons.getStyleClass().add("vboxQuestions");
        vboxDisplay.getChildren().addAll(showAnimal,animal2,buttons);
    }
    
    private void initializeQuestionVBox(){
        vboxDisplay.getChildren().clear();
        vboxQuestions.getStyleClass().add("vboxQuestions");
        numberQuesLabel =  new Label();
        quesLabel = new Label();
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
        hboxButtons.setAlignment(Pos.CENTER);
        hboxButtons.setPadding((new Insets(10)));
        hboxButtons.getChildren().addAll(yesButton, noButtoon);
        vboxDisplay.getChildren().addAll(vboxQuestions, hboxButtons);
        
    }
    
    private void results(){
        //debugging
        System.out.println("Respuestas del jugador: " + playerAnswers);
        System.out.println("Respuestas en el archivo:");
        printer.printMap(answers);
        
        String animal = game.findAnimalFromUsersAnswers(answers, playerAnswers);
        List<String> possibleAnimals = game.findListAnimals(answers, playerAnswers);
        
        if(animal!=null){
            
            setResultsVBox(animal);
            System.out.println(animal);
            
        } else if(possibleAnimals.isEmpty() && animal!=null){
            System.out.println("Respuestas del jugador: " + playerAnswers);
            quesLabel.setText("No se pudo encontrar ningun animal con esas respuestas");
        }
        else if(possibleAnimals.size()==1){
            quesLabel.setText("He adivinado a tu animal, estás pensando en: " + possibleAnimals.get(0));
        }
        else{
            quesLabel.setText("Puedes estar pensando en uno de estos: "+String.join(", ", possibleAnimals));
        }
    }
}

