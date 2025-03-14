package game;

import game.Utils.InputHandler;
import game.Utils.Menu.MainMenu;

public class Main {
    public static void main(String[] args) {
        int selected = 1;
        while (selected != 0) {
            MainMenu.printFormattedMessage("новая игра началась");
            MainGame game = new MainGame(4, 4);
            game.start();
            MainMenu.showStartMenu();
            selected = InputHandler.getIntInput();
        }
        MainMenu.printFormattedMessage("Выход на рабочий стол");
    }
}