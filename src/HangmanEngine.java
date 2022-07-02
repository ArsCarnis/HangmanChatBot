import org.w3c.dom.Text;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class HangmanEngine {


    static final String FILENAME = "Countries.txt";
    private static String[] words = uploadWords();

    private static final String[] gallows = {"+---+\n" +
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
                    "/|\\  |\n" +
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

    public static void play (Scanner scanner, Texts texts){

        String word = findRandomWord();

        char[] placeholders = new char[word.length()];
        for (int i = 0; i < placeholders.length; i++) {
            placeholders[i] = '_';
        }

        int misses = 0;

        char[] missedGuesses =  new char[6];

        while (misses < 6) {
            System.out.print(gallows[misses]);

            System.out.print("Word:   ");
            printPlaceholders(placeholders);
            System.out.print(System.lineSeparator());

            System.out.print("Misses:   ");
            printMissedGuesses(missedGuesses);
            System.out.print(System.lineSeparator() + System.lineSeparator());

            System.out.print("Guess:   ");
            char guess = scanner.nextLine().toLowerCase().charAt(0);
            System.out.print(System.lineSeparator());

            if (checkGuess(word, guess)) {
                updatePlaceholders(word, placeholders, guess);
            } else {
                missedGuesses[misses] = guess;
                misses++;
            }

            if (Arrays.equals(placeholders, word.toCharArray())) {
                System.out.print(gallows[misses]);
                System.out.println("Word:   ");
                printPlaceholders(placeholders);
                texts.printText(System.lineSeparator() + texts.GAME_PLAYER_WON, texts.getName(),texts.getPlace());
                break;
            }

            texts.makeComment(placeholders,misses);

        }

        if (misses == 6) {
            System.out.print(gallows[6]);
            texts.printText(System.lineSeparator() + texts.GAME_PLAYER_LOST, word);
        }
    }

    private static String[] uploadWords() {
        String[] wordsArray = null;
        try {
            wordsArray = Files.lines(Paths.get(FILENAME))
                    .filter(Objects::nonNull)
                    .filter(line -> !line.isEmpty())
                    .toArray(String[]::new);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return wordsArray;
    }

    private static String findRandomWord() {
        int numWords = words.length;
        double randomDouble = Math.random();
        int randomIndex = (int) (randomDouble * numWords);
        return words[randomIndex];
    }

    private static boolean checkGuess(String word, char guess) {

        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) == guess) {
                return true;
            }
        }
        return false;

    }

    private static void updatePlaceholders(String word, char[] placeholders, char guess) {

        for (int j = 0; j < word.length(); j++) {

            if (word.charAt(j) == guess) {
                placeholders[j] = guess;
            }
        }
    }


    private static void printPlaceholders(char[] placeholders) {
        for (int i = 0; i < placeholders.length; i++) {
            System.out.print(" " + placeholders[i]);
        }
        System.out.print("\n");
    }


    private static void printMissedGuesses(char[] misses) {
        for (int i = 0; i < misses.length; i++) {
            System.out.print(misses[i]);
        }
    }

}
