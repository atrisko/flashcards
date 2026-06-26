package com.lernkarten.model;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceCard extends Card {
    
    private List<AnswerOption> answerOptions;

    //Constructor + parent constructor
    public MultipleChoiceCard(String question, String subject) {
        super(question, subject);
        this.answerOptions = new ArrayList<>();
    }

    @Override // This method overrides the checkAnswer method in the Card class
    public boolean checkAnswer(String userInput) {
      for (AnswerOption option : answerOptions){
            if (userInput.equals(option.getText()) && option.isCorrect()) {
                return true;
            }
        }  
      return false; 
    }
    
    public void addAnswerOption(String text, boolean isCorrect) {
        this.answerOptions.add(new AnswerOption(text, isCorrect));
    }

    public List<AnswerOption> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<AnswerOption> answerOptions) {
        this.answerOptions = answerOptions;
    }
}
