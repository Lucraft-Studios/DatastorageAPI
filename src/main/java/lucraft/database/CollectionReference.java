package lucraft.database;

import com.google.gson.Gson;
import lucraft.database.query.Query;
import lucraft.database.query.QueryBuilder;
import lucraft.database.query.QueryReference;
import lucraft.database.query.QuerySnapshot;
import lucraft.database.request.GetRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class CollectionReference {

    private final String databaseId;
    private final String id;

    CollectionReference(String databaseId, String id) {
        this.databaseId = databaseId;
        this.id = id;
    }

    public DocumentReference getDocument(String name) {
        return new DocumentReference(name, this);
    }

    public String getId() {
        return id;
    }

    public String getDatabaseID() {
        return databaseId;
    }

    public Future<CollectionSnapshot> get() {
        FutureTask task = new FutureTask<>(() -> {
            Gson gson = new Gson();
//            Map<String, Object> options = new HashMap<>();
//            options.put("request-type", "get");
//            Map<String, Object> requestMap = new HashMap<>();
//            requestMap.put("document", "*");
//            requestMap.put("collection", getId());
//            requestMap.put("database", getDatabaseID());
//            requestMap.put("options", options);
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(DataStorage.getInstance().socket.getOutputStream()));
//            writer.write(gson.toJson(requestMap) + "\u0017");
//            writer.flush();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(DataStorage.getInstance().socket.getInputStream()));
//            String responseString = reader.readLine();
            String response = DataStorage.getInstance().makeRequest(new GetRequest(databaseId, id, "*"));
            CollectionSnapshot snapshot = gson.fromJson(response, CollectionSnapshot.class);
            if (snapshot.error != null)
                throw new DatabaseError(snapshot.error);
            return snapshot;
        });
        Executors.newSingleThreadExecutor().execute(task);
        return task;
    }

    public QueryReference query(Query query) {
        return new QueryReference(this, query);
    }

}
