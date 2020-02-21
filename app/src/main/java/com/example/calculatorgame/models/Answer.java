package com.example.calculatorgame.models;

import java.io.Serializable;
import java.util.Comparator;

public class Answer implements Serializable {
    private String question;
    private double answer;
    private boolean isRight;

    public Answer() {}

    public Answer(String question, double answer, boolean isRight) {
        this.question = question;
        this.answer = answer;
        this.isRight = isRight;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public double getAnswer() {
        return answer;
    }

    public void setAnswer(double answer) {
        this.answer = answer;
    }

    public boolean isRight() {
        return isRight;
    }

    public void setRight(boolean right) {
        isRight = right;
    }

    @Override
    public String toString() {
        String result = isRight ? "correct" : "incorrect";

        if(answer % 1 == 0)
            return question + " " + (int)answer + ", " + " was " + result;
        else
            return question + " " + answer + ", " + " was " + result;
    }

    public class AnswerCompare implements Comparator<Answer> {

        @Override
        public int compare(Answer a1, Answer a2) {
            return Double.compare(a1.getAnswer(), a2.getAnswer());
//
//            if(a1.getAnswer() < a2.getAnswer())
//                return -1;
//            else if(a1.getAnswer() > a2.getAnswer())
//                return 1;
//            else
//                return 0;
        }

    }

}
