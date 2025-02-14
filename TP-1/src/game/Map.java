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
        cells[n-1][m-1] = new Cell("castle");

        createRoad();
        createObstacles(10);
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

    private void createObstacles(int count) {
        for (int i = 0; i < count; i++) {
            int x = random.nextInt(n);
            int y = random.nextInt(m);

            // Не ставим препятствия на дорогу или замки
            if (!(Objects.equals(cells[x][y].getType(), "road"))) {
                cells[x][y] = new Cell("obstacle");
            }
        }
    }

    public void display() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                System.out.print(cells[i][j] + " ");
            }
            System.out.println();
        }
    }
}