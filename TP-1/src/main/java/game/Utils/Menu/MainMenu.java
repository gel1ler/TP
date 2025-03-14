package game.Utils.Menu;

import game.OwnerType;

import java.util.stream.Collectors;

public class MainMenu extends Menu {
    public static void showStartMenu() {
        println("Начать игру?");
        println("1 - Да\t\t0 - нет");
    }

    public static void printGameEnd(OwnerType looser) {
        println(looser.name() + " проиграл...");
        printFormattedMessage("игра закончилась");
    }
}