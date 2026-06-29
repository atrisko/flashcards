package com.flashcards;
import com.flashcards.model.MultipleChoiceCard;
import com.flashcards.model.OpenQuestionCard;
import com.flashcards.model.AnswerOption;
import com.flashcards.model.AnswerResult;
import java.util.Scanner;

public class Main {
    
    public static void main(String[] args) 
    {
        
        boolean questionAnswered = false;
        AnswerResult result;

        MultipleChoiceCard cardMultipleChoice = new MultipleChoiceCard("Was ist die Hauptstadt von Frankreich?", "Geographie");
        cardMultipleChoice.addAnswerOption("Paris", true);
        cardMultipleChoice.addAnswerOption("London", false);
        cardMultipleChoice.addAnswerOption("Berlin", false);
        cardMultipleChoice.addAnswerOption("Madrid", false);

        OpenQuestionCard cardOpenQuestion = new OpenQuestionCard("Was ist die Hauptstadt von Frankreich?", "Geographie", "Paris");
        
        //Logic for Multiple Choice Cards
        System.out.println("Multiple Choice:");
        System.out.println("Frage: " + cardMultipleChoice.getQuestion());
        System.out.println("Antworten:");
        int index = 1;
        for (AnswerOption option : cardMultipleChoice.getAnswerOptions()) {
            System.out.println(index + ". " + option.getText());
            index++;
        }
        Scanner scanner = new Scanner(System.in);
        
        
        while (!questionAnswered){
            String userInputMC = scanner.nextLine();
            userInputMC = userInputMC.replace(".","" );

            result = cardMultipleChoice.checkAnswer(userInputMC);
        
            if (result == AnswerResult.CORRECT){
                questionAnswered = true;
                System.out.println("Richtig");
            } else if (result == AnswerResult.WRONG) {
                questionAnswered = true;
                System.out.println("Falsch");
            } else if (result == AnswerResult.INVALID_INPUT){
                System.out.println("Ungültige Eingabe, versuch es erneut");
            }
        }
        
        //Logic for Open Question Cards
        questionAnswered = false;
        System.out.println("Open Question:");
        System.out.println("Question: " + cardOpenQuestion.getQuestion());
        System.out.println("Antwort: ");
      
        while (!questionAnswered){
            String userInputOQ = scanner.nextLine(); 
            System.out.println("You said: " + userInputOQ);
            System.out.println("Right Answer: " + cardOpenQuestion.getAnswer());
            System.out.println("Does your answer match the right answer? (yes/no)");

            String userInputYesOrNo = scanner.nextLine();
            
               
            result = cardOpenQuestion.checkAnswer(userInputYesOrNo);
           
            if (result == AnswerResult.CORRECT){
                System.out.println("Richtig");
                questionAnswered = true;
            } else if (result == AnswerResult.WRONG) {
                System.out.println("Falsch");
                questionAnswered = true;
            } else if (result == AnswerResult.INVALID_INPUT){
                System.out.println("Ungültige Eingabe, versuch es erneut");
            }
        }
        scanner.close();
        
    }
}