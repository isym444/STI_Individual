package org.example;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StubDbConnector implements DbConnector {
    @Override
    public void disconnect() {
        System.out.println("Stub being used so no disconnect needed");
    }

    @Override
    public Question getQuestion() throws SQLException {
        Question stubQuestion = new Question(999,"Which composer is still alive?");
        return stubQuestion;
    }

    @Override
    public List<Answer> getAnswersForQuestion(int questionId) throws SQLException {
        Answer stubAnswer1 = new Answer(800,999,"John Williams", true);
        Answer stubAnswer2 = new Answer(801,999,"Bach", true);
        Answer stubAnswer3 = new Answer(802,999,"Mozart", true);
        Answer stubAnswer4 = new Answer(803,999,"Chopin", true);
        List<Answer> stubAnswersList = new ArrayList<>();
        stubAnswersList.add(stubAnswer1);
        stubAnswersList.add(stubAnswer2);
        stubAnswersList.add(stubAnswer3);
        stubAnswersList.add(stubAnswer4);
        return stubAnswersList;
    }

    @Override
    public boolean verifyAnswer(int questionId, int answerId) throws SQLException {
        if(answerId==800) return true;
        return false;
    }
}
