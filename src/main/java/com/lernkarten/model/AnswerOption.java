package com.lernkarten.model;

public class AnswerOption {
    private String text;
    private boolean isCorrect;

    // Constructor for the AnswerOption class
    public AnswerOption(String text, boolean isCorrect) {
        this.text = text;
        this.isCorrect = isCorrect;
    }

    // Getters for the AnswerOption class
    public String getText() {
        return text;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

}
