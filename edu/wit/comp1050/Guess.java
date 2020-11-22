package edu.wit.comp1050;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;

import java.util.*;

public class Guess {
    ArrayList<Integer> pegs = new ArrayList<>();

    public Guess() {
        int codeSize;
        Properties p = Utils.importProperties();
        codeSize = Integer.parseInt(p.getProperty("codeSize","4"));
        Utils.println("New Guess");
        for (int i = 0; i < codeSize; i++){
            pegs.add(-1);
            //Utils.println("peg #" + (i+1) + ": " + pegs.get(i) + " ");
        }
    }
    public Guess(ArrayList<Integer> guess){
        int codeSize;
        Properties p = Utils.importProperties();
        codeSize = Integer.parseInt(p.getProperty("codeSize","4"));
        if (guess.size() != codeSize){
            Utils.println("Guess failed to initialize");
        } else{
            for (int c:guess){
                pegs.add(c);
            }
        }
        //printGuess();
    }

    public void printGuess() {
        Properties p = Utils.importProperties();
        int codeSize = Integer.parseInt(p.getProperty("codeSize","4"));
        for (int i = 0; i < codeSize; i++){
            Utils.println("peg #" + (i+1) + ": " + pegs.get(i) + " ");
        }
    }

    public boolean equals(Guess guess) {
        int i = 0;
        for (int peg: guess.pegs){
            if (peg != pegs.get(i)){
                return false;
            }
            i++;
        }
        return true;
    }

    public ArrayList<Integer> score(Guess answer) {
        ArrayList<Integer> feedback = new ArrayList<>();
        ArrayList<Integer> guessPegs = (ArrayList<Integer>) pegs.clone();
        ArrayList<Integer> answerPegs = (ArrayList<Integer>) answer.pegs.clone();
        for (int i = 0; i < guessPegs.size(); i++){
            if (answerPegs.get(i).equals(guessPegs.get(i))){
                feedback.add(2);
                guessPegs.remove(i);
                answerPegs.remove(i);
                i--;
            }
        }
        for (int i = 0; i < guessPegs.size(); i++){
            for (int k = 0; k < answerPegs.size(); k++){
                if (answerPegs.size() > 0 && guessPegs.size() > 0) {
                    if (guessPegs.get(i).equals(answerPegs.get(k))) {
                        feedback.add(1);
                        answerPegs.remove(k);
                        k--;
                    }
                }
            }
        }
        return feedback;
    }
}
