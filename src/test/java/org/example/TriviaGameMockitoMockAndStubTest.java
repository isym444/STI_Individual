package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testng.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class TriviaGameMockitoMockAndStubTest {
    private TriviaGame game;
    private DbConnector mockDbConnector;
    private InputStream testInput;
    private ByteArrayOutputStream testOutput;
    private Connection mockConn;
    private PreparedStatement mockStmt;
    private ResultSet mockRs;
    private DbConnectorImpl dbConfig;
    @BeforeEach
    void setUp() throws SQLException {
        mockDbConnector = Mockito.mock(DbConnector.class);
        String inputData = "801\nno\n";
        testInput = new ByteArrayInputStream(inputData.getBytes());
        testOutput = new ByteArrayOutputStream();
        this.game = new TriviaGame(mockDbConnector, testInput, new PrintStream(testOutput));

        mockConn = mock(Connection.class);
        mockStmt = mock(PreparedStatement.class);
        mockRs = mock(ResultSet.class);
        when(mockConn.prepareStatement(any(String.class))).thenReturn(mockStmt);
        when(mockStmt.executeQuery()).thenReturn(mockRs);
        dbConfig = new DbConnectorImpl(mockConn);
    }

    @Test
    public void testGetQuestionReturnsQuestion() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getInt("question_id")).thenReturn(1);
        when(mockRs.getString("question_text")).thenReturn("Test Question");
        Question question = dbConfig.getQuestion();
        assertNotNull(question);
        assertEquals(1, question.getQuestionId());
        assertEquals("Test Question", question.getQuestionText());
    }

    @Test
    public void testGetAnswersForQuestion() throws SQLException {
        when(mockRs.next()).thenReturn(true).thenReturn(false);
        when(mockRs.getInt("answer_id")).thenReturn(1);
        when(mockRs.getString("answer_text")).thenReturn("Test Answer");
        when(mockRs.getBoolean("is_correct")).thenReturn(true);
        List<Answer> answerList = dbConfig.getAnswersForQuestion(1);
        assertNotNull(answerList);
        assertEquals(answerList.get(0).getAnswerId(),1);
        assertEquals(answerList.get(0).getAnswerText(),"Test Answer");
        assertEquals(answerList.get(0).isCorrect(),true);
    }
    @Test
    void testVerifyAnswer() throws SQLException {
        when(mockRs.next()).thenReturn(true);
        when(mockRs.getBoolean("is_correct")).thenReturn(true);
        boolean checker = dbConfig.verifyAnswer(1,1);
        Assert.assertEquals(checker, true);
    }
    @Test
    void testGameLogic() throws SQLException {
        Question mockQuestion = new Question(1, "Who composed Fur Elise?");
        when(mockDbConnector.getQuestion()).thenReturn(mockQuestion);
        when(mockDbConnector.getAnswersForQuestion(1)).thenReturn(List.of(new Answer(801, 1, "Beethoven", true)));
        when(mockDbConnector.verifyAnswer(1, 801)).thenReturn(true);

        game.startGame();

        verify(mockDbConnector, times(1)).verifyAnswer(1, 801);
        verify(mockDbConnector, times(1)).getQuestion();
    }

}
