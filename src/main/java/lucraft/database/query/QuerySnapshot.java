package lucraft.database.query;

import lucraft.database.DocumentSnapshot;

import java.util.List;

public class QuerySnapshot {

    private String error;
    private List<DocumentSnapshot> documents;

    String getError() {
        return error;
    }

    public List<DocumentSnapshot> getDocuments() {
        return documents;
    }

}
