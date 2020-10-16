package lucraft.database.request;

import com.google.gson.Gson;

import java.util.Map;

public class SetRequest extends Request {

    private final String database;
    private final String collection;
    private final String document;

    private final Map<String, Object> data;

    private final RequestOption[] options;

    public SetRequest(String database, String collection, String document, Map<String, Object> data, RequestOption... options) {
        this.database = database;
        this.collection = collection;
        this.document = document;
        this.data = data;
        this.options = options;
    }

    @Override
    public String toString() {
        return "set /" + database + "/" + collection + "/" + document + " " + new Gson().toJson(data);
    }
}
