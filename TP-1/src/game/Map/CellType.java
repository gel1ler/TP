package game.Map;

public enum CellType {
    GRASS(1, "🌿"),
    ROAD(0, "🟫"),
    OBSTACLE(Integer.MAX_VALUE, "🚧"),
    PLAYER_CASTLE(0, "🏰"),
    COMPUTER_CASTLE(0, "\uD83C\uDFEF"),
    PLAYER_ZONE(3, "\uD83D\uDD34"), // синий
    COMPUTER_ZONE(3, "\uD83D\uDD35"),
    PLAYER_HERO(3, "\uD83E\uDDB8"),
    PLAYER_UNIT(3, "\uD83E\uDD77"),
    COMPUTER_HERO(3, "\uD83E\uDDB9"),
    COMPUTER_UNIT(3, "\uD83D\uDD74");

    private final int penalty;
    private final String icon;

    CellType(int penalty, String icon) {
        this.penalty = penalty;
        this.icon = icon;
    }

    public int getPenalty() {
        return penalty;
    }

    public String getIcon() {
        return icon;
    }
}