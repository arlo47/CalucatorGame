package com.example.calculatorgame.models;

public class Answer {
    private String question;
    private double answer;
    private boolean isRight;

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
}
