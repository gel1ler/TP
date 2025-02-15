package game.Castle;

import game.Player.Player;

import java.util.ArrayList;
import java.util.List;

public class Shop<T extends Buy> {
    private final Player player;
    private final List<T> availableItems;

    public Shop(Player player, List<T> availableItems) {
        this.player = player;
        this.availableItems = new ArrayList<>(availableItems);
    }

    public void buyItem(T item) {
        if (player.canAfford(item)) {
            player.minusGold(item.getCost());
            System.out.println("Куплено: " + item.getName());
        } else {
            System.out.println("Недостаточно золота для покупки: " + item.getName());
        }
    }

    public void showAvailableItems() {
        System.out.println("----------\nКоличество золота: " + player.getGold());
        System.out.println("\nДоступные позиции:");
        for (int i = 0; i < availableItems.size(); i++) {
            T item = availableItems.get(i);
            System.out.print((i + 1) + " - " + item.getName() + " (" + item.getCost() + " золота) \t\t");
        }
        System.out.println("0 - Выйти");
    }

    public List<T> getAvailableItems() {
        return availableItems;
    }
}
