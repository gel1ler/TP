package game.Map;

public class Cell {
    private CellType type;
    private int penalty;
    private String icon;

    public Cell(CellType type) {
        if (type == CellType.GRASS && Math.random() >= 0.2)
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

    @Override
    public String toString() {
        return icon;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}