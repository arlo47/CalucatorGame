package com.example.calculatorgame.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class AnswerList implements Serializable {

    private ArrayList<Answer> listOfAnswers;
    private int numberOfRightAnswers;
    private int numberOfWrongAnswers;

    private static AnswerList instance;

    private AnswerList() {
        this.listOfAnswers = new ArrayList<>();
    }

    public static AnswerList getInstance() {
        if(instance == null) {
            instance = new AnswerList();
        }
        return instance;
    }

    public ArrayList<Answer> getListOfAnswers() {
        return listOfAnswers;
    }

    public ArrayList<Answer> getListOfRightAnswers() {

        ArrayList<Answer> listOfRightAnswers = new ArrayList<>();

        for(Answer a : listOfAnswers) {
            if(a.isRight())
                listOfRightAnswers.add(a);
        }
        return listOfRightAnswers;
    }

    public ArrayList<Answer> getListOfWrongAnswers() {

        ArrayList<Answer> listOfWrongAnswers = new ArrayList<>();

        for(Answer a : listOfAnswers) {
            if(!a.isRight())
                listOfWrongAnswers.add(a);
        }
        return listOfWrongAnswers;
    }

    public void addToListOfAnswers(Answer a) {
        this.listOfAnswers.add(a);

        if(a.isRight())
            incrementNumberOfRightAnswers();
        else
            incrementNumberOfWrongAnswers();
    }

    public int getNumberOfRightAnswers() {
        return numberOfRightAnswers;
    }

    public void incrementNumberOfRightAnswers() {
        this.numberOfRightAnswers++;
    }

    public int getNumberOfWrongAnswers() {
        return numberOfWrongAnswers;
    }

    public void incrementNumberOfWrongAnswers() {
        this.numberOfWrongAnswers++;
    }

    public String displayAnswerList() {

        StringBuilder answers = new StringBuilder();

        for(Answer a : listOfAnswers) {
            answers.append(a);
            answers.append("\n");
        }
        return answers.toString();
    }

    public String displayRightToWrongComparison() {
        int totalNumberOfAnswers = numberOfRightAnswers + numberOfWrongAnswers;
        return "Total: " + totalNumberOfAnswers + " Right: " + numberOfRightAnswers + " Wrong: " + numberOfWrongAnswers;
    }

    @Override
    public String toString() {
        return "AnswerList{" +
                "listOfAnswers=" + listOfAnswers +
                ", numberOfRightAnswers=" + numberOfRightAnswers +
                ", numberOfWrongAnswers=" + numberOfWrongAnswers +
                '}';
    }
}
