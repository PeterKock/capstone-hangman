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

        // Display current state of the word using placeholders
        System.out.print("Word: ");
        for (char c : placeholders) {
            System.out.print(c + " ");
        }
        System.out.println("\n"); // Add space after word

        // Display list of incorrect guesses
        System.out.print("Misses: ");
        for (char c : missedGuesses) {
            System.out.print(c + " ");
        }
        System.out.println("\n"); // Extra line after misses

        // Prompt user for input
        System.out.print("Guess: ");
        char guess = scanner.next().toLowerCase().charAt(0);

        boolean isCorrect = false;

        // Check if guess exists in the word, update placeholders if it does
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
                placeholders[i] = guess;
                isCorrect = true;
            }
        }

        // If guess was wrong and hasn't been guessed before, track it
        if (!isCorrect && !missedGuesses.contains(guess)) {
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