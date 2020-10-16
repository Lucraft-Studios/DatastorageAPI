package lucraft.database;

public class DatabaseError extends Exception {

    public DatabaseError() {}

    public DatabaseError(String message) {
        super(message);
    }
}
