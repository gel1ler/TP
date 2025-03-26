package game;

public enum GameStatus {
    BATTLE("battle"),
    MAINGAME("mainGame"),
    INCASTLE("inCastle"),
    INVASION("invasion");

    private final String status;
    GameStatus(String owner) {
        this.status = owner;
    }
}
