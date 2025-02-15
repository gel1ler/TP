package game.Player.Heroes;

public class Barbarian extends Hero {
    private int attackBonus;

    public Barbarian() {
        super(0,0,"Варвар", 8);
        this.attackBonus = 5;
        increaseMovementRange(2);
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    public void increaseAttackBonus(int amount) {
        attackBonus += amount;
    }

    @Override
    public String toString() {
        return "Варвар (Дальность перемещения: " + getMovementRange() + ", Атака: " + attackBonus + ")";
    }
}