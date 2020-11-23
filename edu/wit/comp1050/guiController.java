package edu.wit.comp1050;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import jdk.jfr.Event;

import java.awt.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class guiController implements Initializable{
    private final Map<Integer, Paint> pegColors = new HashMap<>();
    @FXML
    private VBox board;

    @FXML
    private void submit(){
        if (mmind.getGuesses() > 0){
            Node currentSubmission = getLastElement(board);
            cementChoice(currentSubmission);
            Guess currentGuess = getGuess(currentSubmission);
            Feedback feedback = mmind.makeAGuess(currentGuess);
            displayScore(currentSubmission, feedback);
            if (feedback.isAnswerCorrect()){
                board.getChildren().add(new Text("You Won!!!"));
            } else{
                board.getChildren().add(generateRow());
            }
        } else{
            Node currentSubmission = getLastElement(board);
            cementChoice(currentSubmission);
            Guess currentGuess = getGuess(currentSubmission);
            Feedback feedback = mmind.makeAGuess(currentGuess);
            displayScore(currentSubmission, feedback);
            if (feedback.isAnswerCorrect()){
                board.getChildren().add(new Text("You Won!!!"));
            } else{
                board.getChildren().add(new Text("Game Over!!!"));
            }
        }
    }

    private HBox generateRow(){
        HBox h = new HBox();
        int index = board.getChildren().size() * mmind.getCodeSize();
        for (int i = 0; i < mmind.getCodeSize(); i++){
            StackPane stackPane = new StackPane();
            Circle c = new Circle(15,pegColors.get(0));
            Button b = new Button("string");
            c.setId("peg"+ index);
            b.setId("pegButton"+ index);
            index++;
            b.setOpacity(0);
            final int[] color = {0};
            b.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    color[0] += 1;
                    color[0] %= mmind.getColors();
                    c.setFill(pegColors.get(color[0]));
                    c.setId(""+color[0]);

                }
            });
            stackPane.getChildren().add(c);
            stackPane.getChildren().add(b);
            h.getChildren().addAll(stackPane);
        }
        return h;
    }

    private void cementChoice(Node hBox){
        if (hBox instanceof HBox) {
            for (Node stackPane : ((HBox) hBox).getChildren()) {
                if (stackPane instanceof StackPane){
                    for (Node child : ((StackPane) stackPane).getChildren()){
                        if (child instanceof Button){
                            if (child.getId().contains("pegButton")) {
                                child.setVisible(false);
                            }
                        }
                    }
                }

            }
        }
    }

    private Guess getGuess(Node HBox){
        ArrayList<Integer> pegs = new ArrayList<>();
        if (HBox instanceof HBox){
            for (Node stack:((HBox) HBox).getChildren()){
                if (stack instanceof StackPane){
                    for (Node child:((StackPane) stack).getChildren()){
                        if (child instanceof Circle){
                            try{
                                int choice = Utils.getFirstKey(pegColors,((Circle) child).getFill());
                                pegs.add(choice);
                            } catch (NullPointerException e){
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
        Guess guess = new Guess(pegs);
        guess.printGuess();
        return guess;
    }

    private Node getLastElement(VBox b){
        return b.getChildren().get(b.getChildren().size()-1);
    }

    private void displayScore(Node hBox,Feedback f){
        if (hBox instanceof HBox){
            HBox HBox = (HBox) hBox;
            VBox currentVbox = new VBox();
            int i = 0;
            for (int bead:f.getScore()){
                if (bead != 0) {
                    VBox v;
                    if (i % 2 == 0){
                        v = new VBox();
                        currentVbox = v;
                    } else{
                        HBox.getChildren().add(currentVbox);
                        v = currentVbox;
                    }
                    Circle c = new Circle(7);
                    if(bead == 2){
                        c.setFill(Paint.valueOf("Black"));
                    } else{
                        c.setFill(Paint.valueOf("Grey"));
                    }
                    v.getChildren().add(c);
                }
                i++;
            }
            if (currentVbox.getChildren().size() == 1){
                HBox.getChildren().add(currentVbox);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        int k;
        if (mmind.allowBlankSpaces()){
            k = 0;
        } else{
            k = -1;
        }
        pegColors.put(k,Paint.valueOf("White")); //white gets tucked into i = -1 when no blank spaces are allowed
        for (int i = k+1; i < mmind.getColors(); i++){
            pegColors.put(i, Color.hsb(110*i,1,1-((double)i%mmind.getColors())/(1.8 * mmind.getColors())));
        }
        submit();
    }
}
