package edu.wit.comp1050;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Parameters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

public class mmind extends Application {
    private static int guesses;
    private static int colors;
    private static int codeSize;
    private static boolean allowDuplicates;
    private static boolean allowBlankSpaces;
    private static Guess secretCode;

    @Override
    public void start(Stage stage) {
        Parent root = null;
        try{
            root = FXMLLoader.load(getClass().getResource("template.fxml"));
        } catch (Exception e){
            e.printStackTrace();
        }
        assert root != null;
        Scene scene = new Scene(root, 640, 480);
        stage.setTitle("Master Mind");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        setupGame();

        //testUnderlyingCode();
        launch(args);
    }

    public static void setupGame(){
        Properties p = Utils.importProperties();
        guesses = Integer.parseInt(p.getProperty("codePegRows","10"));
        colors = Integer.parseInt(p.getProperty("colors","6"));
        codeSize = Integer.parseInt(p.getProperty("codeSize","4"));
        allowDuplicates = Boolean.parseBoolean(p.getProperty("dupsAllowedInCode","true"));
        allowBlankSpaces = Boolean.parseBoolean(p.getProperty("blanksAllowedInCode"));
        secretCode = generateCode();
    }

    public static Guess generateCode(){
        Guess secret = new Guess();
        for (int i = 0; i < secret.pegs.size(); i++){
            secret.pegs.set(i,generateDigit());
        }
        secret.printGuess();
        return secret;
    }

    public static int generateDigit(){
        Random rand = new Random();
        int digit = Math.abs(rand.nextInt()%colors);
        if (allowDuplicates) {
            return digit;
        } else{
            if (secretCode.pegs.contains(digit)){
                return generateDigit();
            } else{
                return digit;
            }
        }
    }

    public static void testUnderlyingCode(){
        Scanner input = new Scanner(System.in);
        int guessNumber = 1;
        while(guesses > 0){
            ArrayList<Integer> guess = new ArrayList<>();
            Utils.println("Make guess #" + guessNumber + ":");
            for (int i = 0; i < codeSize; i++) {
                Utils.print("peg " + (i+1)  + ": ");
                guess.add(input.nextInt());
            }

            Guess g = new Guess(guess);
            //secretCode.printGuess(); //Show answer cheat
            Utils.println("");
            Utils.println(g.score(secretCode));
            if (g.equals(secretCode)){
                Utils.println("YOU WIN!");
                guesses = 0;
            }
            guessNumber++;
            guesses--;
        }
    }

    public static int getCodeSize(){
        return codeSize;
    }
    public static int getColors(){
        return colors;
    }
    public static int getGuesses(){
        return guesses;
    }
    public static boolean allowDuplicates(){return allowDuplicates;}
    public static boolean allowBlankSpaces(){return allowBlankSpaces;}

    public static boolean makeAGuess(Guess guess){
        guesses--;
        return guess.equals(secretCode);
    }
}