package game.Utils;

import game.Utils.Menu.MainMenu;
import game.Utils.Menu.Menu;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static game.Main.saveGame;

public class InputHandler {
    private static Scanner scanner = new Scanner(System.in);

    public static void setScanner(Scanner scanner) {
        InputHandler.scanner = scanner;
    }

    public static int getIntInput() {
        try {
            Menu.print("> ");
            return scanner.nextInt();
        } catch (Exception e) {
            checkForSaveCommand();
            return getIntInput();
        }
    }

    public static String getStringInput() {
        return scanner.nextLine();
    }

    public static void checkForSaveCommand() {
        if (scanner.hasNextLine()) {
            String input = scanner.nextLine().trim();
            if ("SAVE".equalsIgnoreCase(input)) {
                Menu.println("Сохранение игры...");
                saveGame(false);
                Menu.println("Игра сохранена в файл!");
                Menu.print("> ");
            } else {
                scanner = new Scanner(System.in);
            }
        }
    }
}