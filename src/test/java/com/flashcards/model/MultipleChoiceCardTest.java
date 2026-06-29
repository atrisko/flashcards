package com.flashcards.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;



public class MultipleChoiceCardTest {

    MultipleChoiceCard cardMultipleChoice;
    
    @BeforeEach
    public void setup() {
        // Code to be executed before each test
        cardMultipleChoice = new MultipleChoiceCard("Was ist die Hauptstadt von Frankreich?", "Geographie");
        cardMultipleChoice.addAnswerOption("Paris", true);
        cardMultipleChoice.addAnswerOption("London", false);
        cardMultipleChoice.addAnswerOption("Berlin", false);
        cardMultipleChoice.addAnswerOption("Madrid", false);
    }

    @Test
    public void testCheckAnswerCorrect() {
        AnswerResult result = cardMultipleChoice.checkAnswer("1");
        assertEquals(AnswerResult.CORRECT, result);
    }

    @Test
    public void testCheckAnswerWrong() {
        AnswerResult result = cardMultipleChoice.checkAnswer("2");
        assertEquals(AnswerResult.WRONG, result);
    }

    @Test
    public void testCheckAnswerInvalidInput() {
        AnswerResult result = cardMultipleChoice.checkAnswer("abc");
        assertEquals(AnswerResult.INVALID_INPUT, result);
    }
}
