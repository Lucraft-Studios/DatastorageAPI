package lucraft.database;

import java.util.List;

public class CollectionSnapshot {

    private String collectionID;
    private List<DocumentSnapshot> documents;

    public String error;

    public CollectionSnapshot() {}

    public List<DocumentSnapshot> getDocuments() {
        return documents;
    }

}
