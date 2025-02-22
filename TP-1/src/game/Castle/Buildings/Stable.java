package game.Castle.Buildings;

import game.OwnerType;

public class Stable extends Building {
    public Stable() {
        super("Конюшня", 50, OwnerType.PERSON);
    }

    @Override
    public void interact() {
        System.out.println("Взаимодействие с " + this.getName());
    }
}