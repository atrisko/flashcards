package com.flashcards.model;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;

public class OpenQuestionCardTest {

    OpenQuestionCard cardOpenQuestion;
    
    @BeforeEach
    public void setup() {
        // Code to be executed before each test
        cardOpenQuestion = new OpenQuestionCard("Was ist die Hauptstadt von Frankreich?", "Geographie", "Paris");
    }

    @Test
    public void testCheckAnswerCorrect() {
        assertEquals(AnswerResult.CORRECT, cardOpenQuestion.checkAnswer("yes"));
    }

    @Test
    public void testCheckAnswerWrong() {
        assertEquals(AnswerResult.WRONG, cardOpenQuestion.checkAnswer("no"));
    }

    @Test
    public void testCheckAnswerInvalidInput() {
        assertEquals(AnswerResult.INVALID_INPUT, cardOpenQuestion.checkAnswer("maybe"));
    }
}
