package org.example;

import java.util.ArrayList;
import java.util.List;

public class FakeDbConnectorImpl implements DbConnector {

    @Override
    public void disconnect() {
        System.out.println("Fake connection closed.");
    }

    @Override
    public Question getQuestion() {
        return new Question(888,"Who composed Fur Elise?");
    }

    @Override
    public List<Answer> getAnswersForQuestion(int questionId) {
        List<Answer> fakeAnswers = new ArrayList<>();
        fakeAnswers.add(new Answer(801,888,"Beethoven",true));
        fakeAnswers.add(new Answer(802,888,"Wieniawski",false));
        fakeAnswers.add(new Answer(803,888,"Field",false));
        fakeAnswers.add(new Answer(804,888,"Finzi",false));
        return fakeAnswers;
    }

    @Override
    public boolean verifyAnswer(int questionId, int answerId) {
        if(answerId==801){
            return true;
        }
        else{
            return false;
        }
    }
}
