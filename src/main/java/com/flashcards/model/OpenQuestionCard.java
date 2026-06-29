package com.flashcards.model;

public class OpenQuestionCard extends Card{
    private String answer;
    
    public OpenQuestionCard(String question, String subject, String answer ){
        super(question, subject);
        this.answer = answer;
    }

    @Override
    public AnswerResult checkAnswer(String userInput){
        if (userInput.equalsIgnoreCase("yes")) {
            return AnswerResult.CORRECT; // Correct answer
        } else if (userInput.equalsIgnoreCase("no")) {
            return AnswerResult.WRONG; // Incorrect answer
        }

        return AnswerResult.INVALID_INPUT;
        
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
