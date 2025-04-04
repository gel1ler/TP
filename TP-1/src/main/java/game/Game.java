package game;

import game.Map.BattleMap;
import game.Map.MainMap;
import game.Map.Map;
import game.OwnerType;
import game.Player.Entities.Entity;
import game.Utils.InputHandler;
import game.Utils.Menu.GameMenu;
import game.Utils.Menu.Menu;

import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    protected int n, m;

    public Game(int n, int m) {
        this.n = n;
        this.m = m;
    }

    protected HashMap<String, int[]> checkEnemies(int y, int x, Map map, OwnerType owner, int range) {
        HashMap<String, int[]> nearby = new HashMap<>();

        for (int i = Math.max(y - range, 0); i <= Math.min(y + range, n - 1); i++) {
            for (int j = Math.max(x - range, 0); j <= Math.min(x + range, m - 1); j++) {
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

    protected int[] getDirectionToPosition(int y, int x, int targetY, int targetX) {
        int deltaY = Integer.compare(targetY, y);
        int deltaX = Integer.compare(targetX, x);

        int newY = y + deltaY;
        int newX = x + deltaX;

        return new int[]{newY, newX};
    }

    protected boolean move(Entity entity, Map map, boolean auto) {
        int tempMP = entity.getMP();
        while (tempMP > 75) {
            if (!auto) {
                GameMenu.showAvailiableMoves(tempMP);
            }
            int[] newPos;
            int newX = entity.getX();
            int newY = entity.getY();
            boolean isDiagonal = false;

            if (auto) {
                newPos = getDirectionToPosition(entity.getY(), entity.getX(), 0, 0);
                isDiagonal = (Math.abs(newPos[0] - newY) + Math.abs(newPos[1] - newX)) == 2;

                newY = newPos[0];
                newX = newPos[1];
            } else {
                int direction = InputHandler.getIntInput();
                if (direction == 0) return false;

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
                        Menu.wrongChoose();
                }
            }

            if (map.isCellAvailable(newY, newX, !auto)) {
                double cost = map.getPenalty(newY, newX, entity.getOwner());
                cost *= isDiagonal ? Math.sqrt(2) : 1;
                if (tempMP >= cost) {
                    tempMP -= (int) cost;
                    map.moveObject(new int[]{entity.getY(), entity.getX()}, new int[]{newY, newX}, entity.getOwner());
                    entity.setPos(newY, newX);
                    if (!auto) map.render();
                } else {
                    if (!auto) Menu.println("Недостаточно очков передвижения на такой ход.");
                    return false;
                }
            } else {
                return false;
            }
        }
        Menu.println("Кончились очки передвижения на этот ход.");
        return false;
    }
}