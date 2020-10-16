package lucraft.database;

import com.google.gson.Gson;

import java.util.Map;

public class DocumentSnapshot {

    private String id;
    private final boolean exists;
    private final Map<String, Object> data;

    DocumentSnapshot(String id, boolean exists, Map<String, Object> data) {
        this.id = id;
        this.exists = exists;
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public boolean exists() {
        return exists;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public <T> T toObject(Class<T> clazz) {
        return new Gson().fromJson(new Gson().toJson(data), clazz);
    }

}
