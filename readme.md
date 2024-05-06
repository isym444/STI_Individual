# Testing and Software Improvement Individual Submission

## Game description
This is a classical music trivia/quiz game. It consists of a set of multiple choice questions stored together with answers in the trivia.db sqlite database file.

## How to run the game
You can run the game by running the Main file in the src/main/java/org.example package.

On running the game, you should see a command line interface (CLI), which presents a trivia question. You answer by typing in the number, which you believe to be the correct answer and hitting enter. The CLI will then indicate whether you answered correctly or not and add 1 point to your score if you were successful. It will then offer you the option to continue playing the game or stopping. To continue playing the game, you type yes and enter. To stop playing the game, you type no and enter.

## Doubles
I have made a range of doubles for this submission including mocks, stubs and a fake. These have all been used within the different test classes found in the src/test/java/org.example package.

Of note, for the mocks and stubs, I have manually made my own mocks and stubs using the strategy design pattern as taught within the course. These can be found in the src/main/java/org.example package titled as MockDbConnector and StubDbConnector. However, I have also included tests that use the mockito testing framework to create mocks and stubs automatically. These are used within the test/java/org.example/TriviaGameMockitoMockAndStubTest test file. Most of the tests are present within this file.


All the doubles for this game are based around different implementations of the DbConnector interface, which defines the methods responsible for making calls to the sqlite database that stores the trivia game questions and answers.

The methods that can be doubled include a getQuestion method that in the actual DbConnectorImpl, gets a random question from the database's stored questions before using the returned data to construct a new Question object to return. The second method is a getAnswersForQuestion method, which takes in the questionId of a particular question and searches for the corresponding correct answer within the database, which it then returns as an Answer object. Finally, the verifyAnswer method takes in a questionId and answerId (which comes from a user's answer) before making a call to the database to check whether the answerId corresponds as the correct answer for the questionId. This is possible as the "Answers" table in the database has columns for the corresponding question_id as well as an "is_correct" column, which indicates whether it is the correct answer for the particular question it corresponds to.

The mocks return null or equivalent values, stubs return predetermined values when the class methods are called and the fake uses an in memory data structure (in this case an ArrayList) to store/get data on method calls rather than making actual calls to the database.