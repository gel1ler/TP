package game;

import java.util.Objects;
import java.util.Random;

public class Map {
    private int n, m;
    private Cell[][] cells;
    private Random random;

    public Map(int n, int m) {
        this.n = n;
        this.m = m;
        this.cells = new Cell[n][m];
        this.random = new Random();
        init();
    }

    private void init() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                cells[i][j] = new Cell("grass");
            }
        }
        //Castles
        cells[0][0] = new Cell("castle");
        cells[n - 1][m - 1] = new Cell("castle");

        divideMap();
        createRoad();
//        createObstacles(20); // Не симметрично
    }

    private void divideMap() {
        int playerRegionEnd = m / 3; // Левая треть карты - область игрока
        int neutralRegionStart = playerRegionEnd;
        int neutralRegionEnd = 2 * m / 3; // Центральная треть карты - нейтральная область
        int computerRegionStart = neutralRegionEnd; // Правая треть карты - область компьютера

        // Устанавливаем штрафы для нейтральной области
        for (int n = 0; n < 9; n++) {
            for (int i = Math.max(n - 2, 0); i < Math.min(n + 3, cells.length-1); i++) {
                if (i >= 0 && i < cells.length && (i!=0 && n!=0)) {
                    System.out.println(i);
                    cells[n][i] = new Cell("high_penalty_grass");
                }
            }
        }
    }

    private void createRoad() {
        int x = 1;
        int y = 1;

        while (x != n - 1 || y != m - 1) {
            cells[x][y] = new Cell("road");
            if (x < n - 1) x++;
            if (y < m - 1) y++;
        }
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
                System.out.print(cells[i][j] + " ");
            }
            System.out.println();
        }
    }
}