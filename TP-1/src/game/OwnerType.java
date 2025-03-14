package game;

public enum OwnerType {
    PERSON("person"),
    COMPUTER("computer");

    private final String owner;

    OwnerType(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public OwnerType getEnemy() {
        return this == OwnerType.PERSON ? OwnerType.COMPUTER : OwnerType.PERSON;
    }
}