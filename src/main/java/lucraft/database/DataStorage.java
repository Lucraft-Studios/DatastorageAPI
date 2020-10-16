package lucraft.database;

import lucraft.database.request.Request;

import java.io.*;
import java.net.Socket;

public class DataStorage {

    private final int port = 7864;

    private final Socket socket;
    private final BufferedReader reader;
    private final BufferedWriter writer;

    private final Object locker = new Object();

    private static DataStorage instance;

    public static DataStorage getInstance() {
        if (instance == null) throw new NullPointerException("instance not set!");
        return instance;
    }

    public static void setInstance(String serverUrl) throws IOException {
        if (instance != null) {
            instance.socket.close();
            try {
                instance.finalize();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }
        instance = new DataStorage(serverUrl);
    }

    private DataStorage(String url) throws IOException {
        this.socket = new Socket(url, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public String makeRequest(Request request) throws IOException {
        synchronized (locker) {
            writer.write(request.toString() + '\u0017');
            writer.flush();
            return reader.readLine();
        }
    }

    public Database getDatabase(String name) {
        return new Database(name);
    }

}
