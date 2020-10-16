package lucraft.database.request;

import lucraft.database.query.Query;

public class GetRequest extends Request {

    private final String database;
    private final String collection;
    private final String document;
    private final Query query;

    public GetRequest(String database, String collection, String document) {
        this.database = database;
        this.collection = collection;
        this.document = document;
        this.query = null;
    }

    public GetRequest(String database, String collection, Query query) {
        this.database = database;
        this.collection = collection;
        this.document = "*";
        this.query = query;
    }

    @Override
    public String toString() {
        String s = "get /" + database + "/" + collection + "/" + document;
        if (query == null) {
            return s;
        } else {
            return s + " " + query.toString();
        }
    }
}
