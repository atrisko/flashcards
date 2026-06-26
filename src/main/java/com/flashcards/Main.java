package com.flashcards;
import com.flashcards.model.MultipleChoiceCard;
import com.flashcards.model.OpenQuestionCard;
import com.flashcards.model.AnswerOption;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) 
    {
        MultipleChoiceCard cardMultipleChoice = new MultipleChoiceCard("What is the capital of France?", "Geography");
        cardMultipleChoice.addAnswerOption("Paris", true);
        cardMultipleChoice.addAnswerOption("London", false);
        cardMultipleChoice.addAnswerOption("Berlin", false);
        cardMultipleChoice.addAnswerOption("Madrid", false);

        OpenQuestionCard cardOpenQuestion = new OpenQuestionCard("What is the capital of France?", "Geography", "Paris");
        
        //Logic for Multiple Choice Cards
        System.out.println("Multiple Choice:");
        System.out.println("Question: " + cardMultipleChoice.getQuestion());
        System.out.println("Antworten:");
        int index = 1;
        for (AnswerOption option : cardMultipleChoice.getAnswerOptions()) {
            System.out.println(index + ". " + option.getText());
            index++;
        }
        Scanner scanner = new Scanner(System.in);
        String userInputMC = scanner.nextLine();
        userInputMC = userInputMC.replace(".","" );  
        if (cardMultipleChoice.checkAnswer(userInputMC)){
            System.out.println("Correct");
        } else {
            System.out.println("Incorrect");
        }
        
        //Logic for Open Question Cards
        System.out.println("Open Question:");
        System.out.println("Question: " + cardOpenQuestion.getQuestion());
        System.out.println("Antwort: ");
        String userInputOQ = scanner.nextLine();
        System.out.println("You said: " + userInputOQ);
        System.out.println("Right Answer: " + cardOpenQuestion.getAnswer());
        System.out.println("Does your answer match the right answer? (yes/no)");
        String userInput = scanner.nextLine();
        if (cardOpenQuestion.checkAnswer(userInput)){
            System.out.println("Correct");
        } else {
            System.out.println("Incorrect");
        }
        scanner.close();
        
    }
}