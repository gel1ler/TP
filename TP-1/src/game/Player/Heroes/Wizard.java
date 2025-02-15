package game.Player.Heroes;

public class Wizard extends Hero {
    private int magicPower; // Магическая сила

    public Wizard() {
        super(0,0,"Маг", 12);
        this.magicPower = 10;
    }

    public int getMagicPower() {
        return magicPower;
    }

    public void increaseMagicPower(int amount) {
        magicPower += amount;
    }

    @Override
    public String toString() {
        return "Волшебник (Дальность перемещения: " + getMovementRange() + ", Магия: " + magicPower + ")";
    }
}