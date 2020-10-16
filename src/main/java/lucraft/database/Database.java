package lucraft.database;

public class Database {

    private final String id;

    Database(final String id) {
        this.id = id;
    }

    public CollectionReference getCollection(String name) {
        return new CollectionReference(id, name);
    }

}
