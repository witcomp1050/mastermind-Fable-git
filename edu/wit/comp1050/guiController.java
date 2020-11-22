package edu.wit.comp1050;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import jdk.jfr.Event;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class guiController implements Initializable{
    private int index = 0;
    private Map<Integer, Paint> pegColors = new HashMap<>();

    @FXML
    private Button submitButton;
    @FXML
    private Button pegButton;
    @FXML
    private Circle peg;

    @FXML
    private VBox board;

    @FXML
    private void submit(){
        if (mmind.getGuesses() > 0){
            Node currentSubmission = board.getChildren().get(board.getChildren().size()-1);
            cementChoice(currentSubmission);
            Guess currentGuess = getGuess(currentSubmission);
            if (mmind.makeAGuess(currentGuess)){
                board.getChildren().add(new Text("You Won!!!"));
            } else{
                board.getChildren().add(generateRow());
            }
        } else{
            board.getChildren().add(new Text("Game Over!!!"));
        }
    }

    private HBox generateRow(){
        HBox h = new HBox();
        index = board.getChildren().size() * mmind.getCodeSize();
        for (int i = 0; i < mmind.getCodeSize(); i++){
            StackPane stackPane = new StackPane();
            Circle c = new Circle(15,pegColors.get(0));
            Button b = new Button("string");
            c.setId("peg"+index);
            b.setId("pegButton"+index);
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
                            Utils.println("Found Button");
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

    private void displayScore(Node hBox){

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pegColors.put(0,Paint.valueOf("White"));
        for (int i = 1; i < mmind.getColors(); i++){
            pegColors.put(i, Color.hsb(110*i,1,1-((double)i%mmind.getColors())/(1.8 * mmind.getColors())));
        }
        submit();
    }
}
