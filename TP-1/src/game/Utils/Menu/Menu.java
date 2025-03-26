package game.Utils.Menu;

import game.Castle.Buildings.Building;

import java.util.List;
import java.util.stream.Collectors;

public class Menu {
    public static void wrongChoose() {
        println("Неверный выбор.");
    }

    public static void print(String message) {
       System.out.print(message);
    }

    public static void println(String message) {
        System.out.println(message);
    }

    public static void errorMessage(String message) {
        System.err.println(message);
    }

    public static void displayMenu(List<Building> items, boolean priceDisplay) {
        for (int i = 0; i < items.size(); i++) {
            String name = items.get(i).getName();
            int price = items.get(i).getCost();

            if (priceDisplay)
                System.out.printf("%d - %s (%d золота) \t\t", i + 1, name, price);
            else
                System.out.printf("%d - %s \t\t", i + 1, name);
        }
        print("0 - Выйти \n");
    }

    public static <T> void displayArrays(List<T> list){
        for (int i = 0; i < list.size(); i++) {
            println((i + 1) + " - " + list.get(i));
        }
    }

    public static void printFormattedMessage(String message) {
        String border = "=".repeat(message.length() * 2);
        String formattedMessage = message.toUpperCase()
                .chars()
                .mapToObj(c -> (char) c + " ")
                .collect(Collectors.joining())
                .trim();

        println(border);
        println(formattedMessage);
        println(border);
    }
}