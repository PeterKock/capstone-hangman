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
    public static String[] gallows = {"+---+\n" +
        "|   |\n" +
        "    |\n" +
        "    |\n" +
        "    |\n" +
        "    |\n" +
        "=========\n",

        "+---+\n" +
        "|   |\n" +
        "O   |\n" +
        "    |\n" +
        "    |\n" +
        "    |\n" +
        "=========\n",

        "+---+\n" +
        "|   |\n" +
        "O   |\n" +
        "|   |\n" +
        "    |\n" +
        "    |\n" +
        "=========\n",

        " +---+\n" +
        " |   |\n" +
        " O   |\n" +
        "/|   |\n" +
        "     |\n" +
        "     |\n" +
        " =========\n",

        " +---+\n" +
        " |   |\n" +
        " O   |\n" +
        "/|\\  |\n" + // backslash requires escaping: \\
        "     |\n" +
        "     |\n" +
        " =========\n",

        " +---+\n" +
        " |   |\n" +
        " O   |\n" +
        "/|\\  |\n" +
        "/    |\n" +
        "     |\n" +
        " =========\n",

        " +---+\n" +
        " |   |\n" +
        " O   |\n" +
        "/|\\  |\n" + 
        "/ \\  |\n" +
        "     |\n" +
        " =========\n"};

    /**
     * Selects and returns a random word from the static list of animal names.
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
     * Prints the current state of the guessed word with spaces between letters.
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
     * Prints the list of incorrect guesses made by the user.
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

    public static void main(String[] args) {

        // Pick a random word and prepare placeholders
        String word = randomWord(); // The word the user needs to guess
        char[] placeholders = new char[word.length()]; // Array to hold _ _ _ placeholders
        ArrayList<Character> missedGuesses = new ArrayList<>(); // List to track incorrect guesses
        Scanner scanner = new Scanner(System.in);

        // Fill the placeholder array with underscores
            for (int i = 0; i < placeholders.length; i++) {
            placeholders[i] = '_';
        }

        // Main game loop: continues until win or 6 incorrect guesses
            while (true) {
        // Display current gallows state based on number of misses
        System.out.println(gallows[missedGuesses.size()]);

        printPlaceholders(placeholders);

        printMisses(missedGuesses);

        // Prompt user for input
        System.out.print("Guess: ");
        String input = scanner.next().toLowerCase();

        // If input is the full word and correct - win
        if (input.equals(word)) {
            System.out.println("You win! The word was: " + word);
            break;
        }

        // If input is longer than 1 char and incorrect - count as a wrong guess
        if (input.length() > 1) {
            System.out.println("Incorrect word guess.");

            // Add a unique dummy character to represent each wrong full-word guess
            missedGuesses.add('*'); // Using '*' just as a placeholder
            continue;
        }

        // Otherwise treat it as a single letter guess
        char guess = input.charAt(0);


        // Check if the letter was already guessed (either correct or incorrect)
        boolean alreadyGuessed = false;

        // Check if guess is in placeholders (already guessed correctly)
        for (char c : placeholders) {
            if (c == guess) {
                alreadyGuessed = true;
                break;
            }
        }

        // Check if guess is in missedGuesses (already guessed incorrectly)
        if (missedGuesses.contains(guess)) {
            alreadyGuessed = true;
        }

        // If already guessed, skip this turn
        if (alreadyGuessed) {
            System.out.println("You already guessed that letter. Try a different one.\n");
            continue;
        }

        boolean isCorrect = false;

        // Check if guess exists in the word, update placeholders if it does
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
                placeholders[i] = guess;
                isCorrect = true;
            }
        }

        // If guess was wrong, track it
        if (!isCorrect) {
            missedGuesses.add(guess);
        }

        // Display the updated word after processing the guess
        System.out.print("Updated word: ");
        for (char c : placeholders) {
            System.out.print(c + " ");
        }
        System.out.println("\n"); // Add spacing for clarity

        // Check win condition: if there are no underscores left
        if (String.valueOf(placeholders).equals(word)) {
            System.out.println("You win! The word was: " + word);
            break;
        }

        // Check loss condition: 6 incorrect guesses
        if (missedGuesses.size() == 6) {
            System.out.println(gallows[6]);
            System.out.println("You lose! The word was: " + word);
            break;
        }
    }

    scanner.close(); // Close input resource
}

}