package game.Castle;

import game.Castle.Buildings.*;
import game.PLayer.Player;
import game.Utils.Menu.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Castle extends Shop {
    private final List<Building> buildings = new ArrayList<>();

    public Castle(Player player) {
        super(player, createAvailableBuildings(player));
    }

    private static List<Buy> createAvailableBuildings(Player player) {
        List<Buy> availableBuildings = new ArrayList<>();
        availableBuildings.add(new Tavern(player));
        availableBuildings.add(new Hub(player));
        availableBuildings.add(new Stable());
        return availableBuildings;
    }

    public boolean hasBuilding(String name) {
        return buildings.stream().anyMatch(i -> Objects.equals(i.getName(), name));
    }

    public void buyBuilding() {
        Scanner in = new Scanner(System.in);
        showAvailableItems();

        int selected = in.nextInt();
        while (selected != 0) {
            Buy item = getAvailableItems().get(selected - 1);
            if (item instanceof Building && canAfford(item)) {
                buyItem(item);
                buildings.add((Building) item);
            }
            else {
                System.out.println("Недостаточно золота для покупки: " + item.getName());
            }
            showAvailableItems();
            selected = in.nextInt();
        }
    }

    private void enterBuilding() {
        if (!buildings.isEmpty()) {
            Scanner in = new Scanner(System.in);
            System.out.println("==Войти в здание==");
            Menu.displayMenu(buildings, false);
            int selected = in.nextInt();

            while (selected != 0) {
                try {
                    buildings.get(selected - 1).interact();
                } catch (Exception e) {
                    System.out.println("Неверное значение\n");
                }

                Menu.displayMenu(buildings, false);
                selected = in.nextInt();
            }
        } else {
            System.out.println("У вас нет зданий.");
        }
    }

    public void enter() {
        System.out.println("\nВы в замке.");
        Scanner in = new Scanner(System.in);
        String[] mainMenu = {"Купить здание", "Войти в здание"};
        Menu.displayMenu(mainMenu);

        int selected = in.nextInt();
        while (selected != 0) {
            switch (selected) {
                case 1:
                    buyBuilding();
                    break;
                case 2:
                    enterBuilding();
                    break;
            }
            Menu.displayMenu(mainMenu);
            selected = in.nextInt();
        }
    }
}