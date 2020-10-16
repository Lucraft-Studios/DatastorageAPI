package lucraft.database;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.junit.jupiter.api.Assertions.*;

class CollectionSnapshotTest {

    @Test
    void getAllDocumentsInACollection() throws IOException, ExecutionException, InterruptedException {
        DataStorage.setInstance("lucraft.ddns.net");
        Database database = DataStorage.getInstance().getDatabase("store");
        CollectionReference ref = database.getCollection("apps");
        Future<CollectionSnapshot> future = ref.get();
        List<DocumentSnapshot> documents = future.get().getDocuments();

        System.out.println("Document count: " + documents.size());

        for (DocumentSnapshot document : documents) {
            System.out.println(document.getId() + " => " + document.getData());
        }
    }

}