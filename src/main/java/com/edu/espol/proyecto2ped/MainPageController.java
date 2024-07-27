package com.edu.espol.proyecto2ped;


import ClassLists.FileControl;
import ClassLists.Node;
import ClassLists.SearchTree;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;



public class MainPageController implements Initializable{

    @FXML
    private ImageView genieImage;
    @FXML
    private Button noButtoon;
    @FXML
    private Label numberQuesLabel;
    @FXML
    private Label quesLabel;
    @FXML
    private Button yesButton;
    
    private Node<String> currentNode;
    private SearchTree<String> questionTree;
    private int actualNumQues = 0;
    private int maxQuestions;
    private List<String> playerAnswers = new LinkedList<>(); //Sirve para guardar todas las respuestas del usuario

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startGame();
 
    }
        public void startGame() {
        // Obtener el "banco" de preguntas
        Queue<String> queueQuestions = FileControl.readLinesFromFile("questions.txt");
        if (!queueQuestions.isEmpty()) {
            // Crear el árbol de preguntas
            questionTree = new SearchTree<>();
            questionTree = questionTree.buildQuestionTree(queueQuestions);
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
    private void yesButtonQues(ActionEvent event) {
        playerAnswers.add("yes");
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
    private void noButtonQues(ActionEvent event) {
        playerAnswers.add("no");
        if(currentNode.getNo()!=null){
            
            currentNode=currentNode.getNo().getRoot();
            actualNumQues++;
            showQuestion();
        }
        else{
            results();
        }
    }
    
    private void results(){
        //Cargo las respuestas usando un map
        try{
            Map<String,List<String>> answerMap = FileControl.readAnswersFromFile("answers.txt");
            // conf, un iterator para hacer más eficiente el programa xD
                    // Imprimir para depuración
            System.out.println("Respuestas del jugador: " + playerAnswers);
            System.out.println("Respuestas en el archivo:");
            for (Map.Entry<String, List<String>> entry : answerMap.entrySet()) {
                System.out.println(entry.getKey() + " -> " + entry.getValue());
            }
            
            //Busco las coincidencias exactas mediante el mapa (es decir, cuando es si si si)
            Iterator<Map.Entry<String,List<String>>> iterator = answerMap.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,List<String>> mapResult = iterator.next();
                if(mapResult.getValue().equals(playerAnswers)){
                    quesLabel.setText("He adivinado a tu animal, estás pensando en: "+ mapResult.getKey());
                    return;
                }
            }
            
            //Si no hay coincidencias exactas, hay distintos animales que podrían ser de acuerdo a la pregunta ( no se si me expliqué)
            List<String> possibleAnimals = new LinkedList<>();
            iterator = answerMap.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,List<String>> mapResult_2 = iterator.next();
                if(mapResult_2.getValue().containsAll(playerAnswers)){
                    possibleAnimals.add(mapResult_2.getKey());
                }
            }
            if(possibleAnimals.isEmpty()){
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
        catch(IOException e){
            quesLabel.setText("Error al cargar las respuestas");
        }
        
        
        
    }
    
    
    
}
