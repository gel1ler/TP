package game.Map;

public enum CellType {
    GRASS(150, "🌿"),
    ROAD(25, "🟫"),
    OBSTACLE(Integer.MAX_VALUE, "🚧"),
    PERSON_CASTLE(0, "🏰"),
    COMPUTER_CASTLE(0, "\uD83C\uDFEF"),
    PERSON_ZONE(100, "\uD83D\uDD34"), // синий
    COMPUTER_ZONE(100, "\uD83D\uDD35"),
    PERSON_HERO(0, "\uD83E\uDDB8"),
    PERSON_UNIT(0, "\uD83E\uDD20"),
    COMPUTER_HERO(0, "\uD83E\uDDB9"),
    COMPUTER_UNIT(0, "\uD83D\uDE08"),
    FOG(150, "\uD83C\uDF2B"),
    GOLD(150, "🪙");

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