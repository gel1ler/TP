package game;

import game.Map.Map;
import game.OwnerType;
import game.Player.Entities.Entity;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    protected Scanner scanner = new Scanner(System.in);
    protected int n, m;

    public Game(int n, int m) {
        this.n = n;
        this.m = m;
    }

    protected HashMap<String, int[]> checkEnemies(int y, int x, Map map, OwnerType owner, int range) {
        HashMap<String, int[]> nearby = new HashMap<>();

        // Проверяем все соседние клетки (включая диагонали)
        for (int i = Math.max(y - range, 0); i <= Math.min(y + range, n - 1); i++) {
            for (int j = Math.max(x - range, 0); j <= Math.min(x + range, m - 1); j++) {
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

    protected boolean move(Entity entity, Map map, boolean auto) {
        int tempMP = entity.getMP();
        while (tempMP > 25) {
            if (!auto) {
                System.out.println(tempMP + " - очков передвижения");
                System.out.println("Выберите направление:");
                System.out.println("7  8  9");
                System.out.println("4     6");
                System.out.println("1  2  3");
                System.out.println("Или введите 0 для завершения хода");
            }
            Random random = new Random();
            int direction = auto ? random.nextInt(9) : scanner.nextInt();
            if (direction == 0) return false;


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
                    if (!auto) System.out.println("Неверное направление.");
            }

            if (map.isCellAvailable(newY, newX, !auto)) {
                double cost = map.getPenalty(newY, newX);
                cost *= isDiagonal ? Math.sqrt(2) : 1;
                if (tempMP > cost) {
                    tempMP -= (int) cost;
                    map.moveObject(new int[]{entity.getY(), entity.getX()}, new int[]{newY, newX}, entity.getOwner());
                    entity.setPos(newY, newX);
                    if (!auto) map.render();
                } else {
                    if (!auto) System.out.println("Недостаточно очков передвижения на такой ход.");
                }
            }
        }
        System.out.println("Кончились очки передвижения на этот ход.");
        return false;
    }
}