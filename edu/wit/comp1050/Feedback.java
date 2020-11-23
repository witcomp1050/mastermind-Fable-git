package edu.wit.comp1050;

import java.util.ArrayList;

public class Feedback {
    private boolean answerCorrect;
    private ArrayList<Integer> score;

    Feedback(ArrayList<Integer> s, boolean c){
        answerCorrect = c;
        score = s;
    }

    public ArrayList<Integer> getScore() {
        return score;
    }

    public boolean isAnswerCorrect() {
        return answerCorrect;
    }
}
