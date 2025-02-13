class Tavern extends Building {
    public Tavern() {
        super("Таверна", 100); // Стоимость постройки
    }

    @Override
    public void interact(Player player) {
        if (player.getCastle().getUnits().isEmpty()) {
            System.out.println("Нанимаем героя...");
            player.getCastle().addHero(new Hero());
            player.setGold(player.getGold() - 50); // Стоимость найма героя
        } else {
            System.out.println("Невозможно нанять героя: в замке есть другие юниты.");
        }
    }
}