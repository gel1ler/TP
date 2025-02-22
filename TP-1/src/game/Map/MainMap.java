package game.Map;

import game.OwnerType;
import game.Player.Entities.Hero;
import game.Player.Player;

import java.util.*;

public class MainMap extends Map {
    public MainMap(int n, int m, Player person, Player computer) {
        super(n, m, person, computer);
    }

    private void setHeroes(int startY, int startX, Player owner) {
        int[][] directions = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1},{0, -1}, {-1, -1}};
        List<Hero> heroes = owner.getHeroes();
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startY, startX});

        boolean[][] visited = new boolean[objects.length][objects[0].length];
        visited[startY][startX] = true;
        int placedHeroes = 0;

        while (!queue.isEmpty() && placedHeroes < heroes.size()) {
            int[] current = queue.poll();
            int y = current[0], x = current[1];

            if ((x != startX || y != startY) && (objects[y][x] == null || objects[y][x].empty())) {
                objects[y][x] = new Cell(owner.getCellType());
                heroes.get(placedHeroes++).setPos(y, x);
            }

            for (int[] dir : directions) {
                int newY = y + dir[0], newX = x + dir[1];
                if (newX >= 0 && newX < objects.length && newY >= 0 && newY < objects[0].length && !visited[newY][newX]) {
                    visited[newY][newX] = true;
                    queue.add(new int[]{newY, newX});
                }
            }
        }

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
        terrain[0][0] = new Cell(CellType.PERSON_CASTLE);
        terrain[n - 1][m - 1] = new Cell(CellType.COMPUTER_CASTLE);
        createRoad();
        setHeroes(0, 0, person);
        setHeroes(n - 1, m - 1, computer);

//        createObstacles(20); // Не симметрично
    }

    public void updateHeroes(int startX, int startY) {
        int[][] directions = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1},{0, -1}, {-1, -1}};
        for (Hero hero : person.getHeroes()) {
            boolean[][] visited = new boolean[objects.length][objects[0].length];

            if (hero.getY() == 0 && hero.getX() == 0) {
                Queue<int[]> queue = new LinkedList<>();
                queue.add(new int[]{0, 0});

                while (!queue.isEmpty()) {
                    int[] current = queue.poll();
                    int y = current[0], x = current[1];
                    if ((x != startX || y != startY) && (objects[y][x] == null || objects[y][x].empty())) {
                        objects[y][x] = new Cell(CellType.PERSON_HERO);
                        hero.setPos(y, x);
                        break;
                    }

                    for (int[] dir : directions) {
                        int newY = y + dir[0], newX = x + dir[1];
                        if (newX >= 0 && newX < objects.length && newY >= 0 && newY < objects[0].length && !visited[newY][newX]) {
                            visited[newY][newX] = true;
                            queue.add(new int[]{newY, newX});
                        }
                    }
                }
            }
        }
    }
}
