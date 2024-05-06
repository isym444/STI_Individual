package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DatabaseDataGenerator {
    public static void setupDatabase() {
        String url = "jdbc:sqlite:trivia.db";
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                try (Statement stmt = conn.createStatement()) {
                    stmt.execute("CREATE TABLE IF NOT EXISTS Questions (" +
                            "question_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "question_text TEXT NOT NULL," +
                            "difficulty TEXT);");

                    stmt.execute("CREATE TABLE IF NOT EXISTS Answers (" +
                            "answer_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                            "question_id INTEGER," +
                            "answer_text TEXT NOT NULL," +
                            "is_correct BOOLEAN NOT NULL," +
                            "FOREIGN KEY(question_id) REFERENCES Questions(question_id));");

                    String[][] questions = {
                            {"Which composer famously composed a set of 4 ballades?", "Intermediate"},
                            {"Which composer famously had two personalities?", "Advanced"},
                            {"Who composed the Brandenburg Concertos?", "Intermediate"},
                            {"Which composer wrote the opera 'Carmen'?", "Advanced"},
                            {"Who composed the 'Moonlight Sonata'?", "Intermediate"},
                            {"Which composer is known for the 'Rite of Spring'?", "Advanced"},
                            {"Who wrote 'The Four Seasons'?", "Intermediate"},
                            {"Which composer is famous for 'Boléro'?", "Intermediate"},
                            {"Who composed 'Eine kleine Nachtmusik'?", "Intermediate"},
                            {"Who is the composer of 'Symphony No. 94' (Surprise Symphony)?", "Intermediate"}
                    };

                    String[][][] answers = {
                            {{"Chopin", "true"}, {"Beethoven", "false"}, {"Mozart", "false"}, {"Bach", "false"}},
                            {{"Schumann", "true"}, {"Liszt", "false"}, {"Haydn", "false"}, {"Brahms", "false"}},
                            {{"Bach", "true"}, {"Vivaldi", "false"}, {"Telemann", "false"}, {"Handel", "false"}},
                            {{"Bizet", "true"}, {"Verdi", "false"}, {"Puccini", "false"}, {"Rossini", "false"}},
                            {{"Beethoven", "true"}, {"Chopin", "false"}, {"Schubert", "false"}, {"Mendelssohn", "false"}},
                            {{"Stravinsky", "true"}, {"Schoenberg", "false"}, {"Prokofiev", "false"}, {"Shostakovich", "false"}},
                            {{"Vivaldi", "true"}, {"Corelli", "false"}, {"Albinoni", "false"}, {"Locatelli", "false"}},
                            {{"Ravel", "true"}, {"Debussy", "false"}, {"Fauré", "false"}, {"Satie", "false"}},
                            {{"Mozart", "true"}, {"Haydn", "false"}, {"Salieri", "false"}, {"Weber", "false"}},
                            {{"Haydn", "true"}, {"Mozart", "false"}, {"Beethoven", "false"}, {"Schubert", "false"}}
                    };

                    for (int i = 0; i < questions.length; i++) {
                        String insertQuestion = "INSERT INTO Questions (question_text, difficulty) VALUES (?, ?)";
                        try (PreparedStatement pstmt = conn.prepareStatement(insertQuestion, Statement.RETURN_GENERATED_KEYS)) {
                            pstmt.setString(1, questions[i][0]);
                            pstmt.setString(2, questions[i][1]);
                            pstmt.executeUpdate();
                            ResultSet rs = pstmt.getGeneratedKeys();
                            if (rs.next()) {
                                int questionId = rs.getInt(1);
                                // Insert Answers for this Question
                                for (int j = 0; j < 4; j++) {
                                    String insertAnswer = "INSERT INTO Answers (question_id, answer_text, is_correct) VALUES (?, ?, ?)";
                                    try (PreparedStatement pstmtAnswer = conn.prepareStatement(insertAnswer)) {
                                        pstmtAnswer.setInt(1, questionId);
                                        pstmtAnswer.setString(2, answers[i][j][0]);
                                        pstmtAnswer.setBoolean(3, Boolean.parseBoolean(answers[i][j][1]));
                                        pstmtAnswer.executeUpdate();
                                    }
                                }
                            }
                        }
                    }

                    System.out.println("Database has been created and populated successfully.");
                }
            }
        } catch (Exception e) {
            System.out.println("Error during database setup: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        setupDatabase();
    }
}
