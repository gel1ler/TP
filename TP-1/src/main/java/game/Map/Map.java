package game.Map;

import game.OwnerType;
import game.Player.Player;
import game.Utils.Menu.GameMenu;

public class Map {
    protected int n, m;
    protected Cell[][] terrain;
    protected Cell[][] objects;
    protected Player person;
    protected Player computer;

    public Map(int n, int m, Player person, Player computer) {
        this.n = n;
        this.m = m;
        this.terrain = new Cell[n][m];
        this.objects = new Cell[n][m];
        this.person = person;
        this.computer = computer;
    }

    public int getPenalty(int y, int x, OwnerType theOneWhoSteps) {
        int initPenalty = terrain[y][x].getPenalty();

        if (terrain[y][x].getType() == CellType.COMPUTER_ZONE && theOneWhoSteps == OwnerType.PERSON
                || terrain[y][x].getType() == CellType.PERSON_ZONE && theOneWhoSteps == OwnerType.COMPUTER)
            initPenalty = (int) (initPenalty * 1.5);
        return initPenalty;
    }

    protected void divideMap() {
        for (int i = 0; i < n; i++) {
            for (int j = Math.max(i - 2, 0); j < Math.min(i + 3, m); j++) {
                if (j >= 0 && j < n) {
                    if ((i > (n / 2 - 1) && j > m / 2) || i > n / 2) {
                        terrain[i][j] = new Cell(CellType.COMPUTER_ZONE);
                    } else {
                        terrain[i][j] = new Cell(CellType.PERSON_ZONE);
                    }
                }
            }
        }
    }

    protected void createRoad() {
        int y = 1;
        int x = 1;

        while (y != n - 1 || x != m - 1) {
            terrain[y][x] = new Cell(CellType.ROAD);
            if (y < n - 1) y++;
            if (x < m - 1) x++;
        }
    }

    public boolean isCellAvailable(int newY, int newX, boolean errorDisplay) {
        // Проверка выхода за границы карты
        if (newY < 0 || newY >= n || newX < 0 || newX >= m) {
            if (errorDisplay) GameMenu.println("Невозможно переместиться за пределы карты.");
            return false;
        }

        // Проверка на препятствия
//        if (terrain[newY][newX].getType().equals("obstacle")) {
//            GameMenu.println("Невозможно переместиться на препятствие.");
//            return false;
//        }

        if (terrain[newY][newX].getType().equals(CellType.PERSON_CASTLE) || terrain[newY][newX].getType().equals(CellType.COMPUTER_CASTLE)) {
            if (errorDisplay) GameMenu.println("Клетка занята замком.");
            return false;
        }

        // Проверка на занятость клетки другим юнитом
        if (objects[newY][newX] != null) {
            if (errorDisplay) GameMenu.println("Клетка занята другим юнитом.");
            return false;
        }

        return true;
    }

    public boolean isEnemyCastle(int y, int x, OwnerType owner) {
        return owner.equals(OwnerType.COMPUTER) ? (x == 0 && y == 0) : (x == m - 1 && y == n - 1);
    }

    public boolean isEnemy(int y, int x, OwnerType owner) {
        Cell cell = objects[y][x];
        if (cell != null) return cell.getOwner() != owner;
        return false;
    }

    public void reverseCellOwner(int[] recruitCoords){
        objects[recruitCoords[0]][recruitCoords[1]].reverseOwner();
    }

    public void moveObject(int[] oldCords, int[] newCords, OwnerType owner) {
        objects[newCords[0]][newCords[1]] = objects[oldCords[0]][oldCords[1]];
        objects[oldCords[0]][oldCords[1]] = null;

        if (terrain[newCords[0]][newCords[1]].getType() == CellType.GOLD) {
            terrain[newCords[0]][newCords[1]].setType(CellType.GRASS);
            terrain[newCords[0]][newCords[1]].setIcon(CellType.GRASS.getIcon());
            GameMenu.println(owner + " получил 100 золота");
            if (owner == OwnerType.PERSON) {
                person.plusGold(100);
            } else {
                computer.plusGold(100);
            }
        }
    }

    public void kill(int y, int x) {
        this.objects[y][x] = null;
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
                    GameMenu.print(terrain[i][j] + " ");
                } else {
                    GameMenu.print(objects[i][j] + " ");
                }
            }
            GameMenu.println("");
        }
        GameMenu.println("");
    }
}