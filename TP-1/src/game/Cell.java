package game;

public class Cell {
    private String type;
    private int penalty;
    private String icon;

    public Cell(String type){
        this.type = type;
        Object[] cellData = initCell(type);
        this.penalty = (int) cellData[0];
        this.icon = (String) cellData[1];
    }

    private Object[] initCell(String type) {
        switch (type) {
            case "grass": return new Object[]{1, "🌿"}; // Трава
            case "road": return new Object[]{0, "🟫"}; // Дорога
            case "obstacle": return new Object[]{Integer.MAX_VALUE, "🚧"}; // Препятствие
            case "castle": return new Object[]{0, "🏰"}; // Замок
            case "high_penalty_grass": return new Object[]{3, "🌾"}; // Нейтральная область с высоким штрафом
            default: return new Object[]{0, "❓"}; // Неизвестный тип
        }
    }

    public String getType() {
        return type;
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
