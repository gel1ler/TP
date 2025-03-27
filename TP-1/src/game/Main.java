package game;

import DB.Saves.GameSave;
import game.Utils.InputHandler;
import game.Utils.Menu.MainMenu;

import java.io.IOException;

import static DB.Saves.GameSave.writeSave;
import static DB.Users.register;

import game.Map.MapEditor;

public class Main {
    private static MainGame game;
    private static String name;

    public static void saveGame(boolean auto) {
        writeSave(game, auto);
    }

    public static String getUserName() {
        return name;
    }

    public static void main(String[] args) throws IOException {
        MainMenu.registrationForm();
        name = InputHandler.getStringInput();
        register(name);

        boolean isRunning = true;
        while (isRunning) {
            MainMenu.showStartMenu();
            int selected = InputHandler.getIntInput();

            switch (selected) {
                case 1:
                    MainMenu.printFormattedMessage("новая игра началась");
                    game = new MainGame(5, 5);
                    isRunning = false;
                    break;
                case 2:
                    game = GameSave.readSave();
                    isRunning = false;
                    break;
                case 3:
                    MapEditor mapEditor = new MapEditor();
                    mapEditor.start();
                    break;
            }
        }

        assert game != null;
        game.start();
    }
}