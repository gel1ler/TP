package game.Castle.Buildings;

import game.PLayer.Player;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private final Player player;
    private final List<Buy> availableItems;

    public Shop(Player player, List<Buy> availableItems) {
        this.player = player;
        this.availableItems = new ArrayList<>(availableItems);
    }

    public boolean canAfford(Buy item) {
        return player.getGold() >= item.getCost();
    }

    public void buyItem(Buy item) {
        if (canAfford(item)) {
            player.minusGold(item.getCost());
            System.out.println("Куплено: " + item.getName());
        } else {
            System.out.println("Недостаточно золота для покупки: " + item.getName());
        }
    }

    public void showAvailableItems() {
        System.out.println("----------\nКоличество золота: " + player.getGold());
        System.out.println("\nДоступные предметы:");
        for (int i = 0; i < availableItems.size(); i++) {
            Buy item = availableItems.get(i);
            System.out.println((i + 1) + " - " + item.getName() + " (" + item.getCost() + " золота)");
        }
        System.out.println("0 - Выйти");
    }

    public List<Buy> getAvailableItems() {
        return availableItems;
    }
}
