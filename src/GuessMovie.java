import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class GuessMovie {

    public static void main(String[] args) throws IOException {

        String chosenMovie = setUpMovie();
        CheckScore myMovie = new CheckScore(chosenMovie);

        // Guesses
        int guessesOutOfTen = 0;

        // While loop for 10 guesses
        while (guessesOutOfTen < 10) {

            // Print out correct guesses and blank spaces
            System.out.println("You are guessing: "
                    + myMovie.getCorrectGuesses()
                    + " Letters left to guess: "
                    + myMovie.getLettersToGuess());

            // Print out any incorrect letters guessed so far
            System.out.println("You have guessed ("
                    + guessesOutOfTen
                    + ") wrong letters: "
                    + myMovie.getIncorrectGuesses());

            // Request the player input a guess
            System.out.println("Guess a letter: ");

            // Read the players input
            Scanner stringIn = new Scanner(System.in);

            // Read the first letter or number from the input
            char guess = stringIn.next().charAt(0);

            // If the value of 'guess', in lowercase or uppercase, is NOT present within correctGuesses and is NOT
            // present within incorrectGuesses, this is a new character, so execute the body of the loop.
            if (!myMovie.getCorrectGuesses().contains(String.valueOf(Character.toLowerCase(guess))) &&
                    !myMovie.getCorrectGuesses().contains(String.valueOf(Character.toUpperCase(guess))) &&
                    !myMovie.getIncorrectGuesses().contains(String.valueOf(Character.toLowerCase(guess))) &&
                    !myMovie.getIncorrectGuesses().contains(String.valueOf(Character.toUpperCase(guess)))) {

                // Use our instance of CheckScore called myMovie to see if this guess is in the film title
                myMovie.updateScore(guess);

                // Use myMovies getScore getter method to see if score is set to false
                if (!myMovie.getScore()) {
                    // Player didn't find a letter, so increase the value of guessesOutOfTen
                    guessesOutOfTen++;
                }

                // Else the value of 'guess' has been used before,
            } else {
                // so reject it nicely
                System.out.println("You've already used that letter! Try again...");

            }

            // If you run out of guesses, game over
            if (guessesOutOfTen == 10) {
                System.out.println("You ran out of guesses, GAME OVER!!");
            }

            // If you guess all of the letters and you still have guesses left, you win
            if (myMovie.getLettersToGuess() == 0) {
                System.out.println("You have guessed '" + myMovie.getChosenMovie() + "' correctly.");
                break;
            }
        }

    }

    public static String setUpMovie() { //lottery one movie from file
        int movieNo = 0;
        String[] movies;
        movies = new String[100];
        try {
            File file = new File("movies.txt");
            Scanner fileScanner = new Scanner(file);


            while (fileScanner.hasNextLine()) {
                movies[movieNo] = fileScanner.nextLine();
                //System.out.println(movies[movieNo]);
                movieNo++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not appropirate");
        }
        int randomMovieNo = (int) (Math.random() * movieNo);

        return movies[randomMovieNo].trim();


    }
}
class CheckScore{

    // Movie chosen in lottery
    private String chosenMovie;

    private String correctGuesses;

    private boolean score;

    // Letters that have been incorrectly guessed
    private String incorrectGuesses;

    // The number of characters in the movie title. This number reduces by 1 each time a character is found.
    private int lettersToGuess;

    CheckScore(String chosenMovie) {

        this.chosenMovie = chosenMovie;

        incorrectGuesses = "";

        lettersToGuess = 0;

        // Create empty String to hold underscores
        StringBuilder movieToGuess = new StringBuilder();

        for (char blanksAndSpaces : chosenMovie.toCharArray()){

            if (Character.isLetterOrDigit(blanksAndSpaces)) {
                movieToGuess.append("_");
                lettersToGuess++;
            }
            if (Character.isWhitespace(blanksAndSpaces)) {
                movieToGuess.append(" ");
            }
        }
        correctGuesses = movieToGuess.toString();
    }

    void updateScore(char guess) {

        score = false;

        char[] guessedSoFar;
        guessedSoFar = correctGuesses.toCharArray();

        //????????????????????????????????????????????
        for (int x = 0; x < 2; x++) {

            // Whatever case we receive the guessed letter in,
            // change it to lower case for the first check
            if (Character.isUpperCase(guess) && x == 0) {
                guess = Character.toLowerCase(guess);
            }

            // For the second check, change the character to uppercase
            if (x == 1) {
                guess = Character.toUpperCase(guess);
            }

            // Loop through every character in the chosenMovie
            for (int i = 0; i < chosenMovie.length(); i++) {

                // If the guess'ed character is found within the chosenMovie,
                if (chosenMovie.charAt(i) == guess) {

                    // update score to true.
                    score = true;

                    // replace the character at that location within the correctGuesses and,
                    guessedSoFar[i] = chosenMovie.charAt(i);

                    // and reduce the amount of letters left to guess by 1
                    lettersToGuess--;
                }
            }

        }
        if (score) {

            // Update the value of correctGuesses
            correctGuesses = String.valueOf(guessedSoFar);

        } else {

            // If score is false, the guess'ed letter was not found, so add the guess'ed
            // letter to the incorrectGuesses list
            incorrectGuesses += Character.toLowerCase(guess) + " ";
        }

    }

    // Getter method to retrieve the chosen movie
    String getChosenMovie() {
        return chosenMovie;
    }

    // Getter method for the current state of the guesses
    String getCorrectGuesses() {
        return correctGuesses;
    }

    // If true player guessed a letter correctly
    boolean getScore() {
        return score;
    }

    // The letters not in the movie
    String getIncorrectGuesses() {
        return incorrectGuesses;
    }

    // Getter for the number of letters in the title
    int getLettersToGuess() {
        return lettersToGuess;
    }

}




