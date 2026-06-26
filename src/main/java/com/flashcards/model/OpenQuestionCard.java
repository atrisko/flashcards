package com.flashcards.model;

public class OpenQuestionCard extends Card{
    private String answer;
    
    public OpenQuestionCard(String question, String subject, String answer ){
        super(question, subject);
        this.answer = answer;
    }

    @Override
    public boolean checkAnswer(String userInput){
        if (userInput.equalsIgnoreCase("yes")) {
            return true; // Correct answer
        } else if (userInput.equalsIgnoreCase("no")) {
            return false; // Incorrect answer
        }
        return false;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
