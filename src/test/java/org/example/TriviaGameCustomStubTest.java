package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;

import static org.mockito.Mockito.verify;

public class TriviaGameCustomStubTest {
    DbConnector stubDbConnector;
    private TriviaGame game;
    private InputStream testInput;
    private ByteArrayOutputStream testOutput;

    @BeforeEach
    void setUp(){
        this.stubDbConnector = new StubDbConnector();
        String inputData = "800\nno\n";
        testInput = new ByteArrayInputStream(inputData.getBytes());
        testOutput = new ByteArrayOutputStream();
        this.game = new TriviaGame(stubDbConnector,testInput, new PrintStream(testOutput));
    }

    @Test
    void testTest() {
        game.startGame();
        Assert.assertTrue(true);
    }
    @Test
    void testGameLogic() throws SQLException {
        game.startGame();
        Assert.assertTrue(stubDbConnector.verifyAnswer(999,800));
    }

}
