package game;

import game.Map.Map;
import game.OwnerType;
import game.Player.Entities.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public abstract class Game {
    protected Scanner scanner = new Scanner(System.in);
    protected boolean isEnded = false;
    protected int n, m;

    public Game(int n, int m) {
        this.n = n;
        this.m = m;
    }

    protected HashMap<String, int[]> checkEnemies(int y, int x, Map map, OwnerType owner) {
        HashMap<String, int[]> nearby = new HashMap<>();

        // Проверяем все соседние клетки (включая диагонали)
        for (int i = Math.max(y - 1, 0); i <= Math.min(y + 1, n - 1); i++) {
            for (int j = Math.max(x - 1, 0); j <= Math.min(x + 1, m - 1); j++) {
                // Пропускаем текущую клетку героя
                if (i == y && j == x) {
                    continue;
                }

                if (map.isEnemyCastle(i, j, owner))
                    nearby.put("castle", new int[]{i, j});
                if (map.isEnemy(i, j, owner)) {
                    nearby.put("enemy", new int[]{i, j});
                }
            }
        }
        return nearby;
    }

    protected boolean move(Entity entity, Map map) {
        System.out.println("Выберите направление:");
        System.out.println("7  8  9");
        System.out.println("4     6");
        System.out.println("1  2  3");
        int direction = scanner.nextInt();

        int newX = entity.getX();
        int newY = entity.getY();
        boolean isDiagonal = false;

        switch (direction) {
            case 8: // Вверх
                newY--;
                break;
            case 2: // Вниз
                newY++;
                break;
            case 4: // Влево
                newX--;
                break;
            case 6: // Вправо
                newX++;
                break;
            case 7: // Вверх-влево
                newY--;
                newX--;
                isDiagonal = true;
                break;
            case 9: // Вверх-вправо
                newY--;
                newX++;
                break;
            case 1: // Вниз-влево
                newY++;
                newX--;
                isDiagonal = true;
                break;
            case 3: // Вниз-вправо
                newY++;
                newX++;
                isDiagonal = true;
                break;
            default:
                System.out.println("Неверное направление.");
        }

        if (map.isCellAvailable(newY, newX)) {
            double cost = map.getPenalty(newY, newX);
            cost *= isDiagonal ? Math.sqrt(2) : 1;
            if (entity.isEnoughMP((int) cost)) {
//                entity.minusMP((int) cost);
                map.moveObject(new int[]{entity.getY(), entity.getX()}, new int[]{newY, newX}, entity.getOwner());
                entity.setPos(newY, newX);

                //Конец попыток
                return false;
            }
            //Продолжение попыток хода
            return true;
        }
        //Продолжение попыток хода
        return true;
    }

//    protected Entity selectEntity(List<Entity> entities, String name) {
//        if (entities.isEmpty()) {
//            return null;
//        }
//
//        System.out.println("Сделайте выбор:");
//        for (int i = 0; i < entities.size(); i++) {
//            System.out.println((i + 1) + " - " + name + " на (" + entities.get(i).getX() + ", " + entities.get(i).getY() + ")");
//        }
//
//        int selected = scanner.nextInt() - 1;
//        if (selected >= 0 && selected < entities.size()) {
//            return entities.get(selected);
//        } else {
//            System.out.println("Неверный выбор.");
//            return null;
//        }
//    }

    public abstract void start();
}