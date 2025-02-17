package game.Map;

import game.OwnerType;
import game.Player.Entities.Hero;
import game.Player.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

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
                terrain[i][j] = new Cell(CellType.GRASS);
            }
        }
        divideMap();

        //Castles
        terrain[0][0] = new Cell(CellType.PLAYER_CASTLE);
        terrain[n - 1][m - 1] = new Cell(CellType.COMPUTER_CASTLE);
        createRoad();
        setHeroes(0, 0, player);
        setHeroes(m - 1, m - 1, computer);
//        createObstacles(20); // Не симметрично
    }

    private void divideMap() {
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

    private void createRoad() {
        int x = 1;
        int y = 1;

        while (x != m - 1 || y != n - 1) {
            terrain[x][y] = new Cell(CellType.ROAD);
            if (x < m - 1) x++;
            if (y < n - 1) y++;
        }
    }

    private void setHeroes(int startX, int startY, Player owner) {
        List<Hero> heroes = owner.getHeroes();
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY});

        boolean[][] visited = new boolean[objects.length][objects[0].length];
        visited[startX][startY] = true;
        int placedHeroes = 0;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty() && placedHeroes < heroes.size()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];

            // Пропускаем клетку (0, 0)
            if (x == startX && y == startY) {
                for (int[] dir : directions) {
                    int newX = x + dir[0];
                    int newY = y + dir[1];

                    if (newX >= 0 && newX < objects.length && newY >= 0 && newY < objects[0].length && !visited[newX][newY]) {
                        visited[newX][newY] = true;
                        queue.add(new int[]{newX, newY});
                    }
                }
                continue;
            }

            // Если клетка свободна, размещаем героя
            if (objects[x][y] == null || objects[x][y].empty()) {
                objects[x][y] = new Cell(Objects.equals(owner.getName(), "player") ? CellType.PLAYER_HERO : CellType.COMPUTER_HERO);
                heroes.get(placedHeroes).setPos(x, y);
                placedHeroes++;
            }

            // Добавляем соседние клетки в очередь
            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                // Проверяем, что новые координаты в пределах массива и клетка не посещена
                if (newX >= 0 && newX < objects.length && newY >= 0 && newY < objects[0].length && !visited[newX][newY]) {
                    visited[newX][newY] = true;
                    queue.add(new int[]{newX, newY});
                }
            }
        }

        // Если героев больше, чем свободных клеток
        if (placedHeroes < heroes.size()) {
            System.out.println("Недостаточно свободных клеток для размещения всех героев!");
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
}