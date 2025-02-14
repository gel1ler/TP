package game.Castle.Buildings;

public class Hub extends Building {
    public Hub() {
        super("Хаб", 25);
    }

//    @Override
//    public void interact(Hero Hero) {
//        if (Hero.getCastle().getUnits().isEmpty()) {
//            System.out.println("Нанимаем героя...");
//            Hero.getCastle().addHero(new Hero());
//            Hero.setGold(Hero.getGold() - 50); // Стоимость найма героя
//        } else {
//            System.out.println("Невозможно нанять героя: в замке есть другие юниты.");
//        }
//    }
}