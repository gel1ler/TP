package game.Utils.Menu;
import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getIntInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextInt();
    }

    public static String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}