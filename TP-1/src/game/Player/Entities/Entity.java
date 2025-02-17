package game.Player.Entities;

import game.Castle.Buy;
import game.OwnerType;

import java.util.Scanner;

import static game.Game.gameMap;

public class Entity extends Buy {
    private int x = 0;
    private int y = 0;
    private static final Scanner scanner = new Scanner(System.in);

    public Entity(String name, int cost, OwnerType owner) {
        super(name, cost, owner);
    }

    public boolean move() {
        System.out.println("Выберите направление:");
        System.out.println("7  8  9");
        System.out.println("4     6");
        System.out.println("1  2  3");
        int direction = scanner.nextInt();

        int newX = x;
        int newY = y;

        switch (direction) {
            case 8: // Вверх
                newX--;
                break;
            case 2: // Вниз
                newX++;
                break;
            case 4: // Влево
                newY--;
                break;
            case 6: // Вправо
                newY++;
                break;
            case 7: // Вверх-влево
                newX--;
                newY--;
                break;
            case 9: // Вверх-вправо
                newX--;
                newY++;
                break;
            case 1: // Вниз-влево
                newX++;
                newY--;
                break;
            case 3: // Вниз-вправо
                newX++;
                newY++;
                break;
            default:
                System.out.println("Неверное направление.");
        }

        if (gameMap.isCellAvailable(newX, newY)) {
            gameMap.moveObject(new int[]{x, y}, new int[]{newX, newY});
            setPos(newX, newY);
            return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
        //checkAction
    }

    public int[] getPos() {
        return new int[]{x, y};
    }

    public void checkEnemies() {
        int x = getX();
        int y = getY();

        // Проверяем все соседние клетки (включая диагонали)
        for (int i = x - 1; i <= x + 1; i++) {
            for (int j = y - 1; j <= y + 1; j++) {
                // Пропускаем текущую клетку героя
                if (i == x && j == y) {
                    continue;
                }

                if (gameMap.isEnemyCastle(j, i, getOwner()))
                    System.out.println("Следующим ходом вы можете войти во вражеский замок.");
                if(gameMap.isEnemy(j, i, getOwner())) {
                    gameMap.render();
                    System.out.println("Начинается сражение...");
                }
            }
        }
    }
}
