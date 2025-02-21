package game.Map;

import game.Player.Entities.Hero;
import game.Player.Player;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class MainMap extends Map {
    //Place gold

    public MainMap(int n, int m, Player player, Player computer) {
        super(n, m, player, computer);
    }

    private void setHeroes(int startY, int startX, Player owner) {
        List<Hero> heroes = owner.getHeroes();
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startY, startX});

        boolean[][] visited = new boolean[objects.length][objects[0].length];
        visited[startY][startX] = true;
        int placedHeroes = 0;
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty() && placedHeroes < heroes.size()) {
            int[] current = queue.poll();
            int y = current[0];
            int x = current[1];

            // Пропускаем клетку (0, 0)
            if (x == startX && y == startY) {
                for (int[] dir : directions) {
                    int newY = y + dir[0];
                    int newX = x + dir[1];

                    if (newX >= 0 && newX < objects.length && newY >= 0 && newY < objects[0].length && !visited[newY][newX]) {
                        visited[newY][newX] = true;
                        queue.add(new int[]{newY, newX});
                    }
                }
                continue;
            }

            // Если клетка свободна, размещаем героя
            if (objects[y][x] == null || objects[y][x].empty()) {
                objects[y][x] = new Cell(Objects.equals(owner.getName(), "player") ? CellType.PLAYER_HERO : CellType.COMPUTER_HERO);
                heroes.get(placedHeroes).setPos(y, x);
                placedHeroes++;
            }

            // Добавляем соседние клетки в очередь
            for (int[] dir : directions) {
                int newY = y + dir[0];
                int newX = x + dir[1];

                // Проверяем, что новые координаты в пределах массива и клетка не посещена
                if (newX >= 0 && newX < objects.length && newY >= 0 && newY < objects[0].length && !visited[newY][newX]) {
                    visited[newY][newX] = true;
                    queue.add(new int[]{newY, newX});
                }
            }
        }

        // Если героев больше, чем свободных клеток
        if (placedHeroes < heroes.size()) {
            System.out.println("Недостаточно свободных клеток для размещения всех героев!");
        }
    }

    @Override
    protected void init() {
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
        setHeroes(n - 1, m - 1, computer);

        //place gold


//        createObstacles(20); // Не симметрично
    }
}
