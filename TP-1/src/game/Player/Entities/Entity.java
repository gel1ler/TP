package game.Player.Entities;

import game.Castle.Buy;
import game.Map.Map;
import game.OwnerType;

import java.util.Scanner;

public class Entity extends Buy {
    private int x = 0;
    private int y = 0;
    private int movementPoints;
    private final int fullStraightPenalty = 100;
    private final int fullDiagonalPenalty = (int) Math.sqrt(2) * fullStraightPenalty;
    private static final Scanner scanner = new Scanner(System.in);

    public Entity(String name, int cost, int movementPoints, OwnerType owner) {
        super(name, cost, owner);
        this.movementPoints = movementPoints;
    }

    public boolean isEnoughMP(int cost) {
        if (movementPoints - cost > 0) {
            return true;
        } else {
            System.out.println("Недостаточно очков перемещения. Сделайте другой ход.");
            return false;
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return this.y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPos(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int[] getPos() {
        return new int[]{x, y};
    }

    public void minusMP(int cost) {
        this.movementPoints -= cost;
    }
}
