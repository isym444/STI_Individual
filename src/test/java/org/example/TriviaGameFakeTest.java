package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class TriviaGameFakeTest {
    private TriviaGame game;
    private DbConnector fakeDbConnector;
    private InputStream testInput;
    private ByteArrayOutputStream testOutput;

    @BeforeEach
    public void setUp(){
        this.fakeDbConnector = new FakeDbConnectorImpl();
        String inputData = "801\nno\n";
        testInput = new ByteArrayInputStream(inputData.getBytes());
        testOutput = new ByteArrayOutputStream();
        this.game = new TriviaGame(fakeDbConnector, testInput, new PrintStream(testOutput));
    }

    @Test
    void testQuestionAsked() {
        game.startGame();
        assertNotNull(game.getQuestion());
    }

    @Test
    void testOutput(){
        game.startGame();;
        String output = testOutput.toString();
        assertTrue(output.contains("Question: Who composed Fur Elise?"), "The question should be displayed");
        assertTrue(output.contains("Enter the number corresponding to your answer:"), "The prompt to enter answer should be displayed");
        assertTrue(output.contains("Your score: "), "Score display should be part of the output");
        assertTrue(output.contains("Correct!") || output.contains("Wrong answer!"), "Feedback on the answer should be displayed");
        assertTrue(output.contains("Do you want to continue playing? (yes/no)"), "The game should ask if the user wants to continue");
        String expected = "Question: Who composed Fur Elise?\nChoose the correct answer:\n801: Beethoven\n802: Wieniawski\n803: Field\n804: Finzi\nEnter the number corresponding to your answer:\nCorrect!\nYour score: 1\nDo you want to continue playing? (yes/no)\n";
        assertEquals(expected, output);
    }
}