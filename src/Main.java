import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Texts texts = new Texts();
        String temp;
        Scanner scanner = new Scanner(System.in);

        texts.beginTalking(scanner);

        while (true) {
            temp = scanner.nextLine();
            if (temp.equalsIgnoreCase("yes") || temp.equalsIgnoreCase("y")) {
                texts.printText(System.lineSeparator() + System.lineSeparator() + texts.GAME_START_1 + System.lineSeparator());
                HangmanEngine.play(scanner, texts);
                break;

            } else if (temp.equalsIgnoreCase("no") || temp.equalsIgnoreCase("n")) {
                texts.continueTalking(scanner);
                break;

            } else {
                texts.printText(texts.YES_NO_WRONG_ANSWER, texts.getName());
            }
        }
        scanner.close();
    }


}