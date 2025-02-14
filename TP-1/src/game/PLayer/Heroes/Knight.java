package game.PLayer.Heroes;

public class Knight extends Hero {
    public Knight() {
        super("Рыцарь", 10);
        increaseMovementRange(1);
    }

    @Override
    public String toString() {
        return "Рыцарь (Дальность перемещения: " + getMovementRange() + ")";
    }
}