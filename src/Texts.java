import java.text.DecimalFormat;
import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Texts {

    // Beginning

    final String BEGINNING_1 = "Hello! I'm a chatbot and you can call me Ultra Anihilator Prime Super SSS. What's your name?";
    final String BEGINNING_2 = "Nice to meat you, %s! Where are you from?";
    final String BEGINNING_3 = "So, %s from %s, I challenge you to a game of Hangman! Do you accept my challenge?" + System.lineSeparator() + "(yes / no)";
    final String YES_NO_WRONG_ANSWER = "Just a simple answer, my dear %s! Be accurate like a hunting eagle and brief like... no, not briefs... Like a G-string!" + System.lineSeparator() + "Please type: \"yes\" or \"no\".";

    // The game

    final String GAME_START_1 = "Very well! You have 6 chances, try to guess the word letter by letter." +
            " I can give you a hint that it will be a single-word country name.";
    final String[] GAME_GOING_WELL_ARRAY = {
            "You're actually quite good...",
            "I can see I've chosen too easy word for you..."
    };
    final String[] GAME_GOING_BAD_ARRAY = {
            "The end is nigh! Your end.",
            "Take your time and think harder.",
            "Alas, my riddle is too sophisticated..."
    };
    final String GAME_LAST_CHANCE = "That's your last chance, %s. Only one shoot stands between you and the eternal despair!";
    final String GAME_PLAYER_WON = "I must admit you are a worthy opponent, %s from %s! This time you are the winner, but next time you won't be so lucky!";
    final String GAME_PLAYER_LOST = "Muahahaha! I won!" + System.lineSeparator() +
            "The word was: '%s'.";

    // Talking

    final String TALKING_INITIALIZE = "Okay, no problem. We can chit-chat a little bit." + System.lineSeparator() + "How old are you?";
    final String TALKING_AGE_RESPONSE = "Did you know that you're %s times younger than University of Oxford?";
    final String TALKING_AGE_TOO_OLD = "You won't trick me. People don't live that long. Or maybe... "
            + System.lineSeparator() + "Maybe you're a vampire! No... I don't think so..." + System.lineSeparator() + "Come on, tell me your age!";
    final String TALKING_AGE_TOO_YOUNG = "No, no, no! I'm an adult-exclusive chatbot. Tell me your real age or call your legal guardian."
            + System.lineSeparator() + "So, what's your age?";
    final String TALKING_AGE_ERROR = "Something's wrong... Are you sure you know your age?";

    // Ending

    final String ENDING_HOUR = "Oh no, it's %s. It's my usual bed-time...";
    final String ENDING_GOODBYE_1 =  "No, no, no, I'm not bored, %s, you're very interesting human being,really. I'm just sleepy. SLEEPY, alright, not bored.";
    final String ENDING_GOODBYE_2 = "Okay, so goodnight! I bid you farewell, my fellow!";

    // The rest

    private String name;
    private String place;
    private int age;
    Calendar now = Calendar.getInstance();

    boolean isLastWordSpoken = false;

    String calculateYears() {
        final double OXFORD_FOUNDING = 1096;
        double result = (now.get(Calendar.YEAR) - OXFORD_FOUNDING) / age;
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(result);
    }
    String getCurrentTime() {
        Instant instant = Instant.now();
        LocalDateTime ldt = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return ldt.getHour() + ":" + ldt.getMinute() + ":" + ldt.getSecond();
    }

    public String getName() {
        return name;
    }

    public String getPlace() {
        return place;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setAge(int age) {
        this.age = age;
    }

    void printText (String text) {
        System.out.println(text);
    }

    void printText (String text, String arg1) {
        System.out.printf(text + System.lineSeparator(),arg1);
    }

    void printText (String text, String arg1, String arg2) {
        System.out.printf(text + System.lineSeparator(),arg1, arg2);
    }

    void beginTalking (Scanner scanner) {
        printText(BEGINNING_1);
        setName(scanner.nextLine());
        printText(BEGINNING_2,getName());
        setPlace(scanner.nextLine());
        printText(BEGINNING_3,getName(), getPlace());
    }

    void continueTalking (Scanner scanner) {
        printText(TALKING_INITIALIZE);
        while (true) {
            try {
                setAge(scanner.nextInt());
            } catch (InputMismatchException e) {
                printText(TALKING_AGE_ERROR);
                scanner.nextLine();
                continue;
            }

            if (age<18) {
                printText(TALKING_AGE_TOO_YOUNG);
            } else if (age>120) {
                printText(TALKING_AGE_TOO_OLD);
            } else {
                break;
            }
        }
        printText(TALKING_AGE_RESPONSE, calculateYears());
        waitForReader(4000);
        printText(ENDING_HOUR,getCurrentTime());
        waitForReader(4000);
        printText(ENDING_GOODBYE_1, getName());
        waitForReader(5500);
        printText(ENDING_GOODBYE_2);
    }

    void makeComment(char[] placeholders, int misses) {
        Random rand = new Random();
        if (rand.nextInt(9) >=6 && misses!=6) {

            int guessedLetters = 0;
            for (int i =0;i<placeholders.length;i++) {
                if (placeholders[i] != '_') {
                    guessedLetters++;
                }
            }
            double guessedPercentage = (double)guessedLetters / (double)placeholders.length * 100;
            boolean isGoingWell = guessedPercentage - misses * 15 > 15;

            if (misses == 5 && !isLastWordSpoken){
                printText(GAME_LAST_CHANCE,name);
                isLastWordSpoken = true;
            } else if (isGoingWell) {
                printText(GAME_GOING_WELL_ARRAY[rand.nextInt(GAME_GOING_WELL_ARRAY.length)]);
            } else {
                printText(GAME_GOING_BAD_ARRAY[rand.nextInt(GAME_GOING_BAD_ARRAY.length)]);
            }
            System.out.println(System.lineSeparator());

        }


    }
    private void waitForReader(int milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(milliseconds);
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }
}
