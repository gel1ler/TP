package game.Utils.Menu;

import game.Castle.Buildings.Building;

import java.util.List;

public class Menu {
    public static void displayMenu(String[] items) {
        System.out.print("0 - Выйти \t\t");
        for (int i = 0; i < items.length; i++) {
            System.out.print((i + 1) + " - " + items[i] + "\t\t");
        }
    }

    public static void displayMenu(List<Building> items, boolean priceDisplay) {
        System.out.print("0 - Выйти \t\t");
        for (int i = 0; i < items.size(); i++) {
            String name = items.get(i).getName();
            int price = items.get(i).getCost();

            if (priceDisplay)
                System.out.printf("%d - %s (%d золота) \t\t", i + 1, name, price);
            else
                System.out.printf("%d - %s \t\t", i + 1, name);
        }
    }
}