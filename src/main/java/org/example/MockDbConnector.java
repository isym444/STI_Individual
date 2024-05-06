package org.example;

import java.sql.SQLException;
import java.util.List;

public class MockDbConnector implements DbConnector {
    @Override
    public void disconnect() {
        System.out.println("Mock being used so no disconnection needed");
    }

    @Override
    public Question getQuestion() throws SQLException {
        return null;
    }

    @Override
    public List<Answer> getAnswersForQuestion(int questionId) throws SQLException {
        return null;
    }

    @Override
    public boolean verifyAnswer(int questionId, int answerId) throws SQLException {
        return false;
    }
}
