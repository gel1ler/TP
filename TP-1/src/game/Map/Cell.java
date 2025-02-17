package game.Map;

public class Cell {
    private CellType type;
    private int penalty;
    private String icon;

    public Cell(CellType type) {
        this.type = type;
        this.penalty = type.getPenalty();
        this.icon = type.getIcon();
    }

    public CellType getType() {
        try {
            return type;
        } catch (NullPointerException e){
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