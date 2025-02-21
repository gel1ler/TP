package game.Map;

public class Cell {
    private CellType type;
    private int penalty;
    private String icon;

    public Cell(CellType type) {
        if (type == CellType.GRASS && Math.random() >= 0.66)
            this.type = CellType.GOLD;
        else
            this.type = type;

        this.penalty = this.type.getPenalty();
        this.icon = this.type.getIcon();
    }

    public CellType getType() {
        try {
            return type;
        } catch (NullPointerException e) {
            return null;
        }
    }

    public boolean empty() {
        return type == null;
    }

    public int getPenalty() {
        return penalty;
    }

    public String getIcon() {
        return icon;
    }

    @Override
    public String toString() {
        return icon;
    }
}