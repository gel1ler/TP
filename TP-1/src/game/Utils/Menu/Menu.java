package game.Utils.Menu;
import game.Castle.Buildings.Building;
import java.util.List;

public class Menu {
    public static void displayMenu(String[] items) {
        System.out.println("0 - Выйти");
        for (int i = 0; i < items.length; i++) {
            System.out.println((i + 1) + " - " + items[i]);
        }
    }

    public static void displayMenu(List<Building> items, boolean priceDisplay) {
        System.out.println("0 - Выйти");
        for (int i = 0; i < items.size(); i++) {
            String name = items.get(i).getName();
            int price = items.get(i).getCost();

            if (priceDisplay)
                System.out.printf("%d - %s (%d золота)%n", i + 1, name, price);
            else
                System.out.printf("%d - %s%n", i + 1, name);
        }
    }
}