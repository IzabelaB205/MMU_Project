package server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dm.DataModel;
import services.CacheUnitController;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;

public class HandleRequest<T> implements Runnable{
    private Socket socket;
    private CacheUnitController<T> controller;

    public HandleRequest(Socket socket, CacheUnitController<T> controller) {
        this.socket = socket;
        this.controller = controller;
    }

    @Override
    public void run() {
        try (PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()))) {
            Request<DataModel<T>[]> request = getSocketRequest();
            String response = "";
            String action = request.getHeader().get("action");
            DataModel<T>[] dataModels = request.getBody();
            boolean success = true;

            switch (action) {
                case "GET":
                    response = controller.get(dataModels).toString();
                    break;
                case "REMOVE":
                    success = controller.remove(dataModels);
                    break;
                case "UPDATE":
                    success = controller.update(dataModels);
                    break;
            }

            if(!action.equals("STATISTIC") && !action.equals("GET")) {
                response = success ? "Succeeded" : "Failed";
            }

            output.println(response);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Request<DataModel<T>[]> getSocketRequest() throws IOException {
        InputStream inputStream = socket.getInputStream();
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        Type ref = new TypeToken<Request<DataModel<T>[]>>() {
        }.getType();
        return new Gson().fromJson(dataInputStream.readUTF(), ref);
    }
}
