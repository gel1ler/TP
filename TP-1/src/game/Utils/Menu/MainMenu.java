package game.Utils.Menu;

import game.OwnerType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.stream.Collectors;

public class MainMenu extends Menu {
    public static void showStartMenu() {
        println("1 - Новая игра");
        println("2 - Загрузить сохранение");
        println("3 - Редактор карт");
    }

    public static void registrationForm() {
        printFormattedMessage("Регистрация / Вход");
        print("Введите имя: ");
    }

    public static void printGameEnd(OwnerType looser) {
        println(looser.name() + " проиграл...");
        printFormattedMessage("игра закончилась");
    }
}