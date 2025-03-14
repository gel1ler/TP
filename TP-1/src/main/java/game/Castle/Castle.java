package game.Castle;

import game.Castle.Buildings.*;
import game.Player.Player;
import game.Utils.InputHandler;
import game.Utils.Menu.BuildingMenu;
import game.Utils.Menu.Menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class Castle extends Shop<Building> {
    private final List<Building> buildings = new ArrayList<>();
    private final Player player;

    public Castle(Player player) {
        super(player, createAvailableBuildings(player));

        //TEST
        this.player = player;
        buildings.add(new Tavern(player));
        buildings.add(new Hub(player));
    }

    private static List<Building> createAvailableBuildings(Player player) {
        List<Building> availableBuildings = new ArrayList<>();
        availableBuildings.add(new Tavern(player));
        availableBuildings.add(new Hub(player));
        availableBuildings.add(new Stable(player));
        return availableBuildings;
    }

    public boolean hasBuilding(String name) {
        return buildings.stream().anyMatch(i -> Objects.equals(i.getName(), name));
    }

    public void buyBuilding() {
        showAvailableItems();

        int selected = InputHandler.getIntInput();
        while (selected != 0) {
            Building item = getAvailableItems().get(selected - 1);
            if (item != null && player.canAfford(item)) {
                buyItem(item);
                buildings.add((Building) item);
            } else {
                assert item != null;
                BuildingMenu.println("Недостаточно золота для покупки: " + item.getName());
            }
            showAvailableItems();
            selected = InputHandler.getIntInput();
        }
    }

    private void enterBuilding() {
        if (!buildings.isEmpty()) {
            BuildingMenu.showEnterBuilding();
            Menu.displayMenu(buildings, false);
            int selected = InputHandler.getIntInput();

            while (selected != 0) {
                try {
                    buildings.get(selected - 1).interact();
                } catch (Exception e) {
                    System.err.println(e);
                }

                BuildingMenu.showEnterBuilding();
                Menu.displayMenu(buildings, false);
                selected = InputHandler.getIntInput();
            }
        } else {
            BuildingMenu.println("У вас нет зданий.");
        }
    }

    public void enter() {
        BuildingMenu.println("\nВы в замке.");
        String[] menuItems = {"Купить здание", "Войти в здание"};
        BuildingMenu.displayAvailiableBuildings(menuItems);

        int selected = InputHandler.getIntInput();
        while (selected != 0) {
            switch (selected) {
                case 1:
                    buyBuilding();
                    break;
                case 2:
                    enterBuilding();
                    break;
            }
            BuildingMenu.displayAvailiableBuildings(menuItems);
            selected = InputHandler.getIntInput();
        }
    }
}