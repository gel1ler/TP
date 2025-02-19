package game.Map;

import game.OwnerType;
import game.Player.Entities.Entity;
import game.Player.Entities.Hero;
import game.Player.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class Map {
    protected int n, m;
    protected Cell[][] terrain;
    protected Cell[][] objects;
    protected Player player;
    protected Player computer;
//    private Random random;

    public Map(int n, int m, Player player, Player computer) {
        this.n = n;
        this.m = m;
        this.terrain = new Cell[n][m];
        this.objects = new Cell[n][m];
        this.player = player;
        this.computer = computer;
//        this.random = new Random();
        init();
    }

    public int getPenalty(int x, int y){
        return terrain[x][y].getPenalty();
    }


     void init() {}

    protected void divideMap() {
        for (int i = 0; i < n; i++) {
            for (int j = Math.max(i - 2, 0); j < Math.min(i + 3, m); j++) {
                if (j >= 0 && j < n) {
                    if ((i > (n / 2 - 1) && j > m / 2) || i > n / 2) {
                        terrain[i][j] = new Cell(CellType.PLAYER_ZONE);
                    } else {
                        terrain[i][j] = new Cell(CellType.COMPUTER_ZONE);
                    }
                }
            }
        }
    }

    protected void createRoad() {
        int x = 1;
        int y = 1;

        while (x != m - 1 || y != n - 1) {
            terrain[x][y] = new Cell(CellType.ROAD);
            if (x < m - 1) x++;
            if (y < n - 1) y++;
        }
    }



    public boolean isCellAvailable(int newX, int newY) {
        // Проверка выхода за границы карты
        if (newX < 0 || newX >= m || newY < 0 || newY >= n) {
            System.out.println("Невозможно переместиться за пределы карты.");
            return false;
        }

        // Проверка на препятствия
        if (terrain[newX][newY].getType().equals("obstacle")) {
            System.out.println("Невозможно переместиться на препятствие.");
            return false;
        }

        // Проверка на занятость клетки другим юнитом
        if (objects[newX][newY] != null) {
            System.out.println("Клетка занята другим юнитом.");
            return false;
        }

        return true;
    }

//    private void createObstacles() {
//        int playerRegionEnd = m / 3;
//        int neutralRegionStart = playerRegionEnd;
//        int neutralRegionEnd = 2 * m / 3;
//        int computerRegionStart = neutralRegionEnd;
//
//        // Препятствия между областями
//        for (int i = 0; i < n; i++) {
//            // Препятствия между областью игрока и нейтральной областью
//            if (i % 2 == 0) {
//                cells[i][playerRegionEnd] = new Cell("obstacle");
//            }
//
//            // Препятствия между нейтральной областью и областью компьютера
//            if (i % 2 != 0) {
//                cells[i][neutralRegionEnd] = new Cell("obstacle");
//            }
//        }
//    }

    public void render() {
//        System.out.println("\n\n\n\n\n\n\n\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (objects[i][j] == null) {
                    System.out.print(terrain[i][j] + " ");
                } else {
                    System.out.print(objects[i][j] + " ");
                }
            }
            System.out.println();
        }
    }

    public void moveObject(int[] oldCords, int[] newCords) {
        objects[newCords[0]][newCords[1]] = objects[oldCords[0]][oldCords[1]];
        objects[oldCords[0]][oldCords[1]] = null;
    }

    public int getWidth() {
        return m;
    }

    public int getHeight() {
        return n;
    }

    public boolean isEnemyCastle(int x, int y, OwnerType owner) {
        return owner.equals(OwnerType.COMPUTER) ? (x == 0 && y == 0) : (x == m - 1 && y == n - 1);
    }

    public boolean isEnemy(int x, int y, OwnerType owner) {
        Cell cell = objects[y][x];
        if (cell != null)
            return owner == OwnerType.COMPUTER ? cell.getType() == CellType.PLAYER_HERO : cell.getType() == CellType.COMPUTER_HERO;
        return false;
    }

//    public Cell getObject(int[] enemyCoords) {
//        return objects[enemyCoords[0]][enemyCoords[1]];
//    }
}