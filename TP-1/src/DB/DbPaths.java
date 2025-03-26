package DB;

public enum DbPaths {
    USERS("db/users.txt"),
    SAVES("db/saves/"),
    MAPSAVES("db/mapsaves/");

    private final String path;

    DbPaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
