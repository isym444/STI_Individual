package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbConnectorImpl implements DbConnector {
    private static final String URL = "jdbc:sqlite:trivia.db";
    public Connection conn;

    public DbConnectorImpl() {
        try {
            conn = DriverManager.getConnection(URL);
            System.out.println("Connection to SQLite has been established.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public DbConnectorImpl(Connection connection){
        this.conn = connection;
    }

    @Override
    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Disconnected from SQLite.");
            } catch (SQLException e) {
                System.out.println("Error while disconnecting: " + e.getMessage());
            }
        }
    }

    @Override
    public Question getQuestion() throws SQLException {
        String questionQuery = "SELECT question_id, question_text FROM Questions ORDER BY RANDOM() LIMIT 1;";
        try (PreparedStatement questionStmt = conn.prepareStatement(questionQuery);
             ResultSet questionRs = questionStmt.executeQuery()) {
            if (questionRs.next()) {
                int questionId = questionRs.getInt("question_id");
                String questionText = questionRs.getString("question_text");
                return new Question(questionId, questionText);
            } else {
                throw new SQLException("No questions available in the database.");
            }
        }
    }

    @Override
    public List<Answer> getAnswersForQuestion(int questionId) throws SQLException {
        List<Answer> answers = new ArrayList<>();
        String answerQuery = "SELECT answer_id, answer_text, is_correct FROM Answers WHERE question_id = ?;";
        try (PreparedStatement answerStmt = conn.prepareStatement(answerQuery)) {
            answerStmt.setInt(1, questionId);
            try (ResultSet answerRs = answerStmt.executeQuery()) {
                while (answerRs.next()) {
                    int answerId = answerRs.getInt("answer_id");
                    String answerText = answerRs.getString("answer_text");
                    boolean isCorrect = answerRs.getBoolean("is_correct");
                    answers.add(new Answer(answerId, questionId, answerText, isCorrect));
                }
            }
        }
        return answers;
    }


    @Override
    public boolean verifyAnswer(int questionId, int answerId) throws SQLException {
        String checkAnswerQuery = "SELECT is_correct FROM Answers WHERE question_id = ? AND answer_id = ?;";
        try (PreparedStatement checkAnswerStmt = conn.prepareStatement(checkAnswerQuery)) {
            checkAnswerStmt.setInt(1, questionId);
            checkAnswerStmt.setInt(2, answerId);
            try (ResultSet checkAnswerRs = checkAnswerStmt.executeQuery()) {
                return checkAnswerRs.next() && checkAnswerRs.getBoolean("is_correct");
            }
        }
    }

}
