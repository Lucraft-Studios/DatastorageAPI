package lucraft.database.request;

public class DeleteRequest extends Request {

    private final String database;
    private final String collection;
    private final String document;

    public DeleteRequest(String database, String collection, String document) {
        this.database = database;
        this.collection = collection;
        this.document = document;
    }

    @Override
    public String toString() {
        return "delete /" + database + "/" + collection + "/" + document;
    }

}
