package game.Utils;
import game.Utils.Menu.Menu;

import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getIntInput() {
        return scanner.nextInt();
    }

    public static int getIntInput(String prompt) {
        Menu.println(prompt);
        return scanner.nextInt();
    }

    public static String getStringInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine();
    }
}