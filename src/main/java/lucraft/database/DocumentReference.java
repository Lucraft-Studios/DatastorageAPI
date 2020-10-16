package lucraft.database;

import com.google.gson.Gson;
import lucraft.database.request.*;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class DocumentReference {

    private final String id;
    private final CollectionReference parent;

//    private final String getRequestString;
    private final String deleteRequestString;

    private final Gson gson;

    private final Request getRequest;
    private final Request deleteRequest;

    DocumentReference(String id, @NotNull CollectionReference parent) {
        this.id = id;
        this.parent = parent;
        // -----------------
        gson = new Gson();
        getRequest = new GetRequest(parent.getDatabaseID(), parent.getId(), id);
        deleteRequest = new DeleteRequest(parent.getDatabaseID(), parent.getId(), id);


//        Map<String, Object> options = new HashMap<>();
//        options.put("request-type", "get");
//        Map<String, Object> requestMap = new HashMap<>();
//        requestMap.put("document", id);
//        requestMap.put("collection", parent.getId());
//        requestMap.put("database", parent.getDatabaseID());
//        requestMap.put("options", options);
//        getRequestString = gson.toJson(requestMap);
        Map<String, Object> options_delete = new HashMap<>();
        options_delete.put("request-type", "delete");
        Map<String, Object> requestMap_delete = new HashMap<>();
        requestMap_delete.put("document", id);
        requestMap_delete.put("collection", parent.getId());
        requestMap_delete.put("database", parent.getDatabaseID());
        requestMap_delete.put("options", options_delete);
        deleteRequestString = gson.toJson(requestMap_delete);
    }

    public Future<DocumentSnapshot> get() {
        FutureTask<DocumentSnapshot> task = new FutureTask<>(() -> {
            String response = DataStorage.getInstance().makeRequest(getRequest);
            HashMap<String, Object> map = gson.fromJson(response, HashMap.class);
            if (map.get("error") != null)
                throw new DatabaseError((String) map.get("error"));
            return gson.fromJson(response, DocumentSnapshot.class);
        });
        Executors.newSingleThreadExecutor().execute(task);
        return task;
    }

    public Future<WriteResult> set(Map<String, Object> data) throws IOException {
        FutureTask<WriteResult> task = new FutureTask<>(() -> {
//            Instant start = Instant.now();
//            Map<String, Object> options = new HashMap<>();
//            options.put("request-type", "set");
//            options.put("overrideFields", true);
//            options.put("generateIdIfNameIsNotSpecified", true);
//            Map<String, Object> request = new HashMap<>();
//            request.put("document", id);
//            request.put("collection", parent.getId());
//            request.put("database", parent.getDatabaseID());
//            request.put("options", options);
//            request.put("data", data);
//            String queryString = new Gson().toJson(request);

//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(DataStorage.getInstance().socket.getOutputStream()));
//            writer.write(queryString + "\u0017");
//            writer.flush();
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(DataStorage.getInstance().socket.getInputStream()));
//            System.out.println(reader.readLine());

//            Instant end = Instant.now();
            String response = DataStorage.getInstance().makeRequest(
                    new SetRequest(
                            parent.getDatabaseID(),
                            parent.getId(),
                            id,
                            data,
                            RequestOption.OVERRIDE_IF_EXISTS,
                            RequestOption.CREATE_IF_NOT_EXISTS
                    ));
            
            return gson.fromJson(response, WriteResult.class);
        });
        Executors.newSingleThreadExecutor().execute(task);
        return task;
    }

    public Future<WriteResult> delete() {
        FutureTask<WriteResult> task = new FutureTask<>(() -> {
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(DataStorage.getInstance().socket.getOutputStream()));
//            writer.write(deleteRequestString + "\u0017");
//            writer.flush();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(DataStorage.getInstance().socket.getInputStream()));
//            String responseString = reader.readLine();

            //HashMap<String, Object> response = new Gson().fromJson(responseString, HashMap.class);
            String response = DataStorage.getInstance().makeRequest(deleteRequest);
            WriteResult writeResult = gson.fromJson(response, WriteResult.class);
            if (writeResult.getError() != null)
                throw new DatabaseError(writeResult.getError());
            return writeResult;
        });
        Executors.newSingleThreadExecutor().execute(task);
        return task;
    }

}

//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(DataStorage.getInstance().socket.getOutputStream()));
//            writer.write(getRequestString + "\u0017");
//            writer.flush();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(DataStorage.getInstance().socket.getInputStream()));
//            String responseString = reader.readLine();
