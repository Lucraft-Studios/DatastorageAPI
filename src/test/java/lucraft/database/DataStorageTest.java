package lucraft.database;

import lucraft.database.query.Query;
import lucraft.database.query.QueryBuilder;
import lucraft.database.query.QuerySnapshot;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class DataStorageTest {

    @Test
    void createConnection() throws IOException {
        DataStorage.setInstance("lucraft.ddns.net");
    }

    @Test
    void getData() throws ExecutionException, InterruptedException, IOException {
        DataStorage.setInstance("lucraft.ddns.net");
        Database db = DataStorage.getInstance().getDatabase("store");
        DocumentReference ref = db.getCollection("apps").getDocument("daytimer");
        Future<DocumentSnapshot> future = ref.get();
        DocumentSnapshot document = future.get();

        if (document.exists()) {
            System.out.println("Document " + document.getId() + " --> Data: " + document.getData());
        } else {
            System.out.println("No such document");
        }
    }

    @Test
    void setData() throws IOException, ExecutionException, InterruptedException {
        DataStorage.setInstance("lucraft.ddns.net");
        Database db = DataStorage.getInstance().getDatabase("store");
        DocumentReference ref = db.getCollection("apps").getDocument("ticketsystem");
        Map<String, Object> data = new HashMap<>();
        data.put("name", "TicketSystem");
        data.put("version", "Early Access (v0.6.23.344)");
        Future<WriteResult> future = ref.set(data);
        WriteResult result = future.get();
        System.out.println("update time: " + result.getUpdateTime());
    }

    @Test
    void deleteDocument() throws IOException, ExecutionException, InterruptedException {
        DataStorage.setInstance("lucraft.ddns.net");
        Database db = DataStorage.getInstance().getDatabase("store");
        DocumentReference ref = db.getCollection("apps").getDocument("ticketsystem");
        Future<WriteResult> future = ref.delete();
        System.out.println(future.get().getUpdateTime());
    }

}