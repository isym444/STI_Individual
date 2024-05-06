package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TriviaGameCustomMockTest {
    DbConnector mockDbConnector;
    private TriviaGame game;
    private InputStream testInput;
    private ByteArrayOutputStream testOutput;

    @BeforeEach
    void setUp(){
        this.mockDbConnector = new MockDbConnector();
        String inputData = "800\nno\n";
        testInput = new ByteArrayInputStream(inputData.getBytes());
        testOutput = new ByteArrayOutputStream();
        this.game = new TriviaGame(mockDbConnector,testInput, new PrintStream(testOutput));
    }

    @Test
    void testVerifyAnswerAlwaysFalse() throws SQLException {
        assertFalse(mockDbConnector.verifyAnswer(999, 800), "verifyAnswer should always return false, indicating the answer is incorrect");
    }

    @Test
    void testNullQuestionHandling() {
        assertThrows(NullPointerException.class, () -> game.startGame(),
                "Game should throw NullPointerException if question is null");
    }
}
