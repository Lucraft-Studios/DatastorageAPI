package lucraft.database.query;

import com.google.gson.Gson;
import lucraft.database.CollectionReference;
import lucraft.database.DataStorage;
import lucraft.database.DatabaseError;
import lucraft.database.request.GetRequest;

import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class QueryReference {

    private final CollectionReference collection;
    private final Query query;

    public QueryReference(CollectionReference collectionReference, Query query) {
        this.collection = collectionReference;
        this.query = query;
    }

    public Future<QuerySnapshot> get() {
        FutureTask<QuerySnapshot> task = new FutureTask<>(() -> {
            Gson gson = new Gson();
//            Map<String, Object> options = new HashMap<>();
//            options.put("request-type", "query");
//            options.put("query-string", query.toString());
//            Map<String, Object> requestMap = new HashMap<>();
//            requestMap.put("document", "*");
//            requestMap.put("collection", collection.getId());
//            requestMap.put("database", collection.getDatabaseID());
//            requestMap.put("options", options);
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(DataStorage.getInstance().socket.getOutputStream()));
//            writer.write(gson.toJson(requestMap) + "\u0017");
//            writer.flush();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(DataStorage.getInstance().socket.getInputStream()));
//            String responseString = reader.readLine();
            String response = DataStorage.getInstance().makeRequest(new GetRequest(collection.getDatabaseID(), collection.getId(), query));
            QuerySnapshot snapshot = gson.fromJson(response, QuerySnapshot.class);
            if (snapshot.getError() != null)
                throw new DatabaseError(snapshot.getError());
            return snapshot;
        });
        Executors.newSingleThreadExecutor().execute(task);
        return task;
    }

}
