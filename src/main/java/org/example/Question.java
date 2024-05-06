package org.example;

public class Question {
    public int QuestionId;
    public String QuestionText;

    public Question(int questionId, String questionText) {
        QuestionId = questionId;
        QuestionText = questionText;
    }

    public int getQuestionId() {
        return QuestionId;
    }

    public void setQuestionId(int questionId) {
        QuestionId = questionId;
    }

    public String getQuestionText() {
        return QuestionText;
    }

    public void setQuestionText(String questionText) {
        QuestionText = questionText;
    }
}
