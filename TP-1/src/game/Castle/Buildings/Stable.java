package game.Castle.Buildings;

public class Stable extends Building {
    public Stable() {
        super("Конюшня", 50);
    }

    @Override
    public void interact() {
        System.out.println("Взаимодействие с " + this.getName());
    }
}