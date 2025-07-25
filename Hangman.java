import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Hangman {

    /**
    * An array of animal names used as potential target words in the game.
    * One word is randomly selected from this list at the start of each game.
    */
    public static String[] words = {"ant", "baboon", "badger", "bat", "bear", "beaver", "camel",
        "cat", "clam", "cobra", "cougar", "coyote", "crow", "deer",
        "dog", "donkey", "duck", "eagle", "ferret", "fox", "frog", "goat",
        "goose", "hawk", "lion", "lizard", "llama", "mole", "monkey", "moose",
        "mouse", "mule", "newt", "otter", "owl", "panda", "parrot", "pigeon", 
        "python", "rabbit", "ram", "rat", "raven","rhino", "salmon", "seal",
        "shark", "sheep", "skunk", "sloth", "snake", "spider", "stork", "swan",
        "tiger", "toad", "trout", "turkey", "turtle", "weasel", "whale", "wolf",
        "wombat", "zebra"};

    /**
    * An array of strings representing the Hangman gallows at each stage of incorrect guesses.
    * Each index corresponds to the number of wrong guesses made (0 to 6).
    */
    public static String[] gallows = {
    "\n +---+\n" +
    " |   |\n" +
    "     |\n" +
    "     |\n" +
    "     |\n" +
    "     |\n" +
    "=========\n",

    "\n +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "     |\n" +
    "     |\n" +
    "     |\n" +
    "=========\n",

    "\n +---+\n" +
    " |   |\n" +
    " O   |\n" +
    " |   |\n" +
    "     |\n" +
    "     |\n" +
    "=========\n",

    "\n +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|   |\n" +
    "     |\n" +
    "     |\n" +
    "=========\n",

    "\n +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + // backslash requires escaping: \\
    "     |\n" +
    "     |\n" +
    "=========\n",

    "\n +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + // backslash requires escaping: \\
    "/    |\n" +
    "     |\n" +
    "=========\n",

    "\n +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + // backslash requires escaping: \\
    "/ \\  |\n" + // backslash requires escaping: \\
    "     |\n" +
    "=========\n"
};

    /**
     * The main method initializes the game state, handles user input,
     * and controls the overall game loop.
     *
     * Game Flow Overview:
     * 1. Initializes word, placeholders, and missed guesses
     * 2. Loops until the game ends (win or 6 mistakes)
     *  2a. Displays gallows, placeholders, and misses each round
     *  2b. Gets and processes user input (letter or full-word guess)
     *  2c. Checks for win/loss conditions
     */

    public static void main(String[] args) {

        // 1. Pick a random word and prepare placeholders
        String word = randomWord();

        // Array to hold _ _ _ placeholders
        char[] placeholders = new char[word.length()];

        // List to track incorrect guesses
        ArrayList<Character> missedGuesses = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);

            // Fill the placeholder array with underscores
            for (int i = 0; i < placeholders.length; i++) {
            placeholders[i] = '_';
        }

        // 2. Main game loop: continues until win or 6 incorrect guesses
        while (true) {

            // 2a. Display current gallows state based on number of misses
            System.out.println(gallows[missedGuesses.size()]);

            // Display the current state of the word with guessed letters and underscores
            printPlaceholders(placeholders);

            // Display all incorrect guesses made so far
            printMisses(missedGuesses);

            // 2b. Prompt user for input
            String input = getUserInput(scanner);

            // If the user enters more than one character, treat it as a full-word guess.
            // If correct, end the game. If incorrect, add a penalty and skip the rest of the loop.
            if (input.length() > 1) {
                if (handleFullWordGuess(input, word, missedGuesses)) break;
                continue;
            }

            // Otherwise treat it as a single letter guess
            char guess = input.charAt(0);

            // Check if the letter has already been guessed (either correct or incorrect)
            if (alreadyGuessed(guess, placeholders, missedGuesses)) {
                System.out.println("You already guessed that letter. Try a different one.\n");
                continue;
            }

            // Try updating the placeholders with the guessed letter
            // If the guess is incorrect (not found in the word), add it to missed guesses
            boolean isCorrect = updatePlaceholders(guess, word, placeholders);
            if (!isCorrect) {
                missedGuesses.add(guess);
            }

            // 2c. Check win condition: if there are no underscores left
            if (checkWin(word, placeholders)) {
                System.out.println();
                System.out.println("You win! The word was: " + word);
                System.out.println();
                break;
            }

            // Check loss condition: 6 incorrect guesses
            if (missedGuesses.size() == 6) {
                System.out.println(gallows[6]);
                System.out.println();
                System.out.println("You lose! The word was: " + word);
                System.out.println();
                break;
            }
        }

    // Close input resource
    scanner.close();
}
    
    // -----------------------------------------------------------------------------
    // Method Overview (Helper Functions)
    // -----------------------------------------------------------------------------
    // 1. randomWord()           - Randomly selects a target word from a predefined list
    // 2. printPlaceholders()    - Displays the current word progress (e.g., "_ a _ _")
    // 3. printMisses()          - Displays all incorrect guesses made so far
    // 4. alreadyGuessed()       - Checks if a guess (letter) was already attempted
    // 5. updatePlaceholders()   - Reveals guessed letters in the correct positions
    // 6. checkWin()             - Determines if the full word has been guessed
    // 7. getUserInput()         - Handles user input and normalizes it to lowercase
    // 8. handleFullWordGuess()  - Processes full-word guesses and updates state
    // -----------------------------------------------------------------------------
    
    /**
     * 1. Selects and returns a random word from the static list of animal names.
     * This method uses java.util.Random to generate a random index.
     *
     * @return A randomly selected animal name from the words array.
     */
    public static String randomWord() {
        Random random = new Random();
        int randomIndex = random.nextInt(words.length);
        return words[randomIndex];
    }

    /**
     * 2. Prints the current state of the guessed word with spaces between letters.
     *
     * @param placeholders The array representing correct guesses and blanks.
     */
    public static void printPlaceholders(char[] placeholders) {
        System.out.print("Word: ");
        for (char c : placeholders) {
            System.out.print(c + " ");
        }
        System.out.println("\n"); // Add space after the word
    }

    /**
     * 3. Prints the list of incorrect guesses made by the user.
     *
     * @param missedGuesses A list containing incorrect letter guesses or '*' for wrong full-word attempts.
     */
    public static void printMisses(ArrayList<Character> missedGuesses) {
        System.out.print("Misses: ");
        for (char c : missedGuesses) {
            System.out.print(c + " ");
        }
        System.out.println("\n"); // Extra line after misses
    }

    /**
     * 4. Checks if the user has already guessed this character (either correctly or incorrectly).
     *
     * @param guess The letter to check.
     * @param placeholders The array of correctly guessed letters.
     * @param missedGuesses The list of incorrect guesses.
     * @return true if the letter has already been guessed, false otherwise.
     */
    public static boolean alreadyGuessed(char guess, char[] placeholders, ArrayList<Character> missedGuesses) {
        // Check if letter is already revealed
        for (char c : placeholders) {
            if (c == guess) {
                return true;
            }
        }

        // Check if letter is in missed guesses
        return missedGuesses.contains(guess);
    }

    /**
     * 5. Updates the placeholders array if the guessed letter is in the word.
     *
     * @param guess The guessed letter.
     * @param word The target word.
     * @param placeholders The current placeholder array.
     * @return true if the guess was correct, false otherwise.
     */
    public static boolean updatePlaceholders(char guess, String word, char[] placeholders) {
        boolean isCorrect = false;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
                placeholders[i] = guess;
                isCorrect = true;
            }
        }
        return isCorrect;
    }

    /**
     * 6. Checks if the player has guessed the entire word.
     *
     * @param word The target word.
     * @param placeholders The current placeholder array.
     * @return true if all letters have been guessed, false otherwise.
     */
    public static boolean checkWin(String word, char[] placeholders) {
        for (int i = 0; i < word.length(); i++) {
            if (placeholders[i] != word.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 7. Prompts the user to enter a guess and returns it as a lowercase string.
     *
     * @param scanner The Scanner object used to read user input.
     * @return The user's input in lowercase.
     */
    public static String getUserInput(Scanner scanner) {
        System.out.print("Enter a letter to guess: ");
        return scanner.next().toLowerCase();
    }

    /**
     * 8. Handles full-word guesses by the user. If correct, ends the game.
     * If incorrect, adds a penalty marker and returns false.
     *
     * @param input The user's input.
     * @param word The correct word to guess.
     * @param missedGuesses List of missed guesses to update on failure.
     * @return true if the game should end, false otherwise.
     */
    public static boolean handleFullWordGuess(String input, String word, ArrayList<Character> missedGuesses) {
        if (input.equals(word)) {
            System.out.println();
            System.out.println("You win! The word was: " + word);
            System.out.println();

            // game ends
            return true;
        } else {
            System.out.println();
            System.out.println("\nIncorrect word guess.");
            System.out.println();
            
            // Add a penalty marker
            missedGuesses.add('*');
            return false;
        }
    }

}