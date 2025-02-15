package game;

import game.Player.Player;

import java.util.Random;

public class Map {
    private int n, m;
    private Cell[][] terrain;
    private Cell[][] objects;
    private Cell[][] result;
    private Player player;
    private Player computer;
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

    private void init() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                terrain[i][j] = new Cell("grass");
            }
        }
        divideMap();

        //Castles
        terrain[0][0] = new Cell("castle");
        terrain[n - 1][m - 1] = new Cell("castle");
        createRoad();
        setHeroes();

//        createObstacles(20); // Не симметрично
    }

    private void divideMap() {
        for (int i = 0; i < n; i++) {
            for (int j = Math.max(i - 2, 0); j < Math.min(i + 3, m); j++) {
                if (j >= 0 && j < n) {
                    if ((i > (n / 2 - 1) && j > m / 2) || i > n / 2) {
                        terrain[i][j] = new Cell("player_zone");
                    } else {
                        terrain[i][j] = new Cell("computer_zone");
                    }
                }
            }
        }
    }

    private void createRoad() {
        int x = 1;
        int y = 1;

        while (x != m - 1 || y != n - 1) {
            terrain[x][y] = new Cell("road");
            if (x < m - 1) x++;
            if (y < n - 1) y++;
        }
    }

    private void setHeroes() {
        player.getHeroes().forEach(i -> {
            objects[i.getX()][i.getY()] = new Cell("player_" + i.getName());
        });
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

    public void display() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (objects[i][j]==null){
                    System.out.print(terrain[i][j] + " ");
                }
                else{
                    System.out.print(objects[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}