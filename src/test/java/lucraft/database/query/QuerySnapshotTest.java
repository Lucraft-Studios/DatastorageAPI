package lucraft.database.query;

import lucraft.database.CollectionReference;
import lucraft.database.DataStorage;
import lucraft.database.Database;
import lucraft.database.DocumentSnapshot;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class QuerySnapshotTest {

    @Test
    void query() throws IOException, ExecutionException, InterruptedException {
        DataStorage.setInstance("lucraft.ddns.net");
        Database database = DataStorage.getInstance().getDatabase("store");
        CollectionReference ref = database.getCollection("apps");

        Future<QuerySnapshot> future = ref.query(new QueryBuilder().where("name").isEqualTo("Daytimer").build()).get();
        QuerySnapshot snapshot = future.get();

        for (DocumentSnapshot document : snapshot.getDocuments()) {
            System.out.println("Query matched at document " + document.getId() + " -> " + document.getData());
        }
    }

}