package org.example;
public class Main {
    public static void main(String[] args) {
        TriviaGame game = new TriviaGame(new DbConnectorImpl(), System.in, System.out);
        game.startGame();
    }
}
