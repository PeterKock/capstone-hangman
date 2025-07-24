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

    String word = randomWord(); // The word the user needs to guess
    char[] placeholders = new char[word.length()]; // Array to hold _ _ _ placeholders
    ArrayList<Character> missedGuesses = new ArrayList<>(); // List of wrong guesses

    for (int i = 0; i < placeholders.length; i++) {
        placeholders[i] = '_';
    }

    // Display the initial word state with underscores
    System.out.print("Word: ");
    for (char c : placeholders) {
        System.out.print(c + " ");
    }
    System.out.println("\n"); // Add spacing for clarity

    Scanner scanner = new Scanner(System.in);

    // Prompt user for input
    System.out.print("Enter a letter: ");
    char guess = scanner.next().charAt(0);
    System.out.println("You guessed: " + guess);

    // Check if guess is in the word
    boolean isCorrect = false;
    for (int i = 0; i < word.length(); i++) {
        if (word.charAt(i) == guess) {
            isCorrect = true;
            // (Have to update placeholders)
        }
    }

    // If not correct, track the miss
    if (!isCorrect) {
        missedGuesses.add(guess);
    }

    // Show missed letters
    System.out.print("Misses: ");
    for (char c : missedGuesses) {
        System.out.print(c + " ");
    }
    System.out.println();

    scanner.close();
}

}