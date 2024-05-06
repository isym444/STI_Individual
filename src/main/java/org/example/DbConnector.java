package org.example;

import java.sql.SQLException;
import java.util.List;

public interface DbConnector {
    void disconnect();
    Question getQuestion() throws SQLException;
    List<Answer> getAnswersForQuestion(int questionId) throws SQLException;

    boolean verifyAnswer(int questionId, int answerId) throws SQLException;

}
