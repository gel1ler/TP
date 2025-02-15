package game.Player.Heroes;

public class Knight extends Hero {
    public Knight() {
        super(0,0,"Рыцарь", 10);
        increaseMovementRange(1);
    }

    @Override
    public String toString() {
        return "Рыцарь (Дальность перемещения: " + getMovementRange() + ")";
    }
}