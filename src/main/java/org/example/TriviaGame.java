package org.example;
import java.io.InputStream;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TriviaGame {
    private int score = 0;
    DbConnector dbConnectorInstance;

    Question question;

    private Scanner scanner;
    private PrintStream out;
    public Question getQuestion() {
        return question;
    }

    public TriviaGame(DbConnector dbConnector, InputStream input, PrintStream output) {
        this.dbConnectorInstance = dbConnector;
        this.scanner = new Scanner(input);
        this.out = output;
    }

    public void startGame() {
        try {
            while (true) {

                this.question = dbConnectorInstance.getQuestion();
                this.out.println("Question: " + question.getQuestionText());

                this.out.println("Choose the correct answer:");

                List<Answer> answersForCurQuestion = dbConnectorInstance.getAnswersForQuestion(question.getQuestionId());
                for(Answer ansChoice : answersForCurQuestion){
                    this.out.println(ansChoice.getAnswerId() + ": " + ansChoice.getAnswerText());
                }
                int userAnswerId = -1;
                while (true) {
                    this.out.println("Enter the number corresponding to your answer:");
                    if (scanner.hasNextInt()) {
                        userAnswerId = scanner.nextInt();
                        scanner.nextLine();
                        break;
                    } else {
                        this.out.println("Invalid input. Please enter a number.");
                        scanner.nextLine();
                    }
                }

                if(dbConnectorInstance.verifyAnswer(question.getQuestionId(), userAnswerId)){
                    this.out.println("Correct!");
                    score++;
                }
                else{
                    this.out.println("Wrong answer!");
                }

                this.out.println("Your score: " + score);
                this.out.println("Do you want to continue playing? (yes/no)");
                String userDecision = scanner.next().trim();
                if (!"yes".equalsIgnoreCase(userDecision)) {
                    dbConnectorInstance.disconnect();
                    break;
                }
            }
        } catch (SQLException e) {
            this.out.println("Error during the game: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
    }
}
